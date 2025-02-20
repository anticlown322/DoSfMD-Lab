package by.andreikaras.silkroadapp.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.andreikaras.silkroadapp.entities.FavouriteModel
import by.andreikaras.silkroadapp.entities.GoodModel
import by.andreikaras.silkroadapp.entities.UserModel
import by.andreikaras.silkroadapp.ui.home.bottom_menu.BottomMenu
import by.andreikaras.silkroadapp.ui.home.bottom_menu.BottomMenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navData: HomeScreenDataObject,
    onGoodClick: (String, GoodModel) -> Unit,
    onProfileClick: (UserModel) -> Unit,
    onSettingsClick: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val coroutineScope = rememberCoroutineScope()

    val selectedBottomItemState = remember {
        mutableStateOf(BottomMenuItem.Home.route)
    }

    val goodsListState = remember {
        mutableStateOf(emptyList<GoodModel>())
    }

    val db = remember { Firebase.firestore }

    val currCategory = remember {
        mutableStateOf("")
    }

    val isFavListEmpty = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        getAllFavIds(db, navData.uid) { favs ->
            getAllGoods(db, favs) { goods ->
                isFavListEmpty.value = goods.isEmpty()
                goodsListState.value = goods
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(Modifier.fillMaxWidth(0.8f)) {
                DrawerHeader()
                DrawerBody(
                    onCategoryClick = { category ->
                        currCategory.value = category

                        getAllFavIds(db, navData.uid) { favs ->
                            if (category == "All") {
                                getAllGoods(db, favs) { goods ->
                                    goodsListState.value = goods
                                }
                            } else {
                                getAllGoodsByCategory(db, favs, category) { goods ->
                                    goodsListState.value = goods
                                }
                            }
                        }

                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomMenu(
                    selectedBottomItemState.value,
                    onFavsClick = {
                        selectedBottomItemState.value = BottomMenuItem.Favourite.route

                        getAllFavIds(db, navData.uid) { favs ->
                            getAllFavGoods(db, favs) { goods ->
                                isFavListEmpty.value = goods.isEmpty()
                                goodsListState.value = goods
                            }
                        }
                    },
                    onHomeClick = {
                        selectedBottomItemState.value = BottomMenuItem.Home.route

                        getAllFavIds(db, navData.uid) { favs ->
                            getAllGoods(db, favs) { goods ->
                                isFavListEmpty.value = goods.isEmpty()
                                goodsListState.value = goods
                            }
                        }
                    },
                    onProfileClick = {
                        selectedBottomItemState.value = BottomMenuItem.Profile.route

                        val userModel: UserModel = UserModel(
                            accId = navData.uid,
                            email = navData.email
                        )

                        onProfileClick(userModel)
                    },
                    onSettingsClick = {
                        selectedBottomItemState.value = BottomMenuItem.Settings.route
                        onSettingsClick()
                    }
                )
            }
        ) { paddingValues ->
            if (isFavListEmpty.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Empty list")
                }

            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(goodsListState.value) { good ->
                    GoodListItem(
                        good,
                        onGoodClick = {
                            onGoodClick(navData.uid, it)
                        },
                        onFavouriteClick = {
                            goodsListState.value = goodsListState.value.map { gd ->
                                if (gd.key == good.key) {
                                    onFavs(
                                        db,
                                        navData.uid,
                                        FavouriteModel(gd.key),
                                        !gd.isFavourite
                                    )
                                    gd.copy(isFavourite = !gd.isFavourite)
                                } else {
                                    gd
                                }
                            }

                            if (selectedBottomItemState.value == BottomMenuItem.Favourite.route) {
                                goodsListState.value =
                                    goodsListState.value.filter { it.isFavourite }
                                isFavListEmpty.value = goodsListState.value.isEmpty()
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun getAllGoods(
    db: FirebaseFirestore,
    idsList: List<String>,
    onGoods: (List<GoodModel>) -> Unit
) {
    db.collection("goods")
        .get()
        .addOnSuccessListener { task ->
            val goodsList = task.toObjects(GoodModel::class.java).map {
                if (idsList.contains(it.key)) {
                    it.copy(isFavourite = true)
                } else {
                    it
                }
            }
            onGoods(goodsList)
        }
        .addOnFailureListener { task ->
            Log.d("MyLog", "Get collection failure: ${task.message}")
        }
}

private fun getAllGoodsByCategory(
    db: FirebaseFirestore,
    idsList: List<String>,
    category: String,
    onGoods: (List<GoodModel>) -> Unit
) {
    db.collection("goods")
        .whereEqualTo("category", category)
        .get()
        .addOnSuccessListener { task ->
            val goodsList = task.toObjects(GoodModel::class.java).map {
                if (idsList.contains(it.key)) {
                    it.copy(isFavourite = true)
                } else {
                    it
                }
            }
            onGoods(goodsList)
        }
        .addOnFailureListener { task ->
            Log.d("MyLog", "Get collection failure: ${task.message}")
        }
}

private fun getAllFavGoods(
    db: FirebaseFirestore,
    idsList: List<String>,
    onGoods: (List<GoodModel>) -> Unit
) {
    if (idsList.isNotEmpty()) {
        db.collection("goods")
            .whereIn("key", idsList)
            .get()
            .addOnSuccessListener { task ->
                val goodsList = task.toObjects(GoodModel::class.java).map {
                    if (idsList.contains(it.key)) {
                        it.copy(isFavourite = true)
                    } else {
                        it
                    }
                }
                onGoods(goodsList)
            }
            .addOnFailureListener { task ->
                Log.d("MyLog", "Get collection failure: ${task.message}")
            }
    } else {
        onGoods(emptyList())
    }
}

private fun onFavs(
    db: FirebaseFirestore,
    uid: String,
    favourite: FavouriteModel,
    isFav: Boolean
) {
    if (isFav) {
        db.collection("users")
            .document(uid)
            .collection("favs")
            .document(favourite.key)
            .set(favourite)
    } else {
        db.collection("users")
            .document(uid)
            .collection("favs")
            .document(favourite.key)
            .delete()
    }
}

private fun getAllFavIds(
    db: FirebaseFirestore,
    uid: String,
    onFavs: (List<String>) -> Unit
) {
    db.collection("users")
        .document(uid)
        .collection("favs")
        .get()
        .addOnSuccessListener { task ->
            val idsList = task.toObjects(FavouriteModel::class.java)
            val keysList = arrayListOf<String>()
            idsList.forEach {
                keysList.add(it.key)
            }
            onFavs(keysList)
        }
}