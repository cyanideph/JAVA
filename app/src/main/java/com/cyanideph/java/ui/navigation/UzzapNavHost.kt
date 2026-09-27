package com.cyanideph.java.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.cyanideph.java.ui.screens.*
object Routes{const val LOGIN="login";const val REGISTER="register";const val MAIN="main";const val BUDDIES="buddies";const val MESSAGES="messages";const val ROOMS="rooms";const val ROOM="room/{name}";const val SETTINGS="settings";const val HELP="help";const val PROFILE="profile";const val STATUS="status";const val HISTORY="history";const val MENUS="menus"}
@Composable fun UzzapNavHost(navController:NavHostController){NavHost(navController,startDestination=Routes.LOGIN){
composable(Routes.LOGIN){LoginScreen{navController.navigate(Routes.MAIN){popUpTo(Routes.LOGIN){inclusive=true}}}}
composable(Routes.MAIN){MainMenuScreen({navController.navigate(Routes.BUDDIES)},{navController.navigate(Routes.MESSAGES)},{navController.navigate(Routes.ROOMS)},{navController.navigate(Routes.SETTINGS)},{navController.navigate(Routes.HELP)})}
composable(Routes.BUDDIES){BuddyListScreen{navController.popBackStack()}}
composable(Routes.MESSAGES){MessagesScreen{navController.popBackStack()}}
composable(Routes.ROOMS){ChatRoomsScreen({navController.popBackStack()}){n->navController.navigate("room/"+java.net.URLEncoder.encode(n,"UTF-8"))}}
composable(Routes.ROOM){e->ChatRoomScreen(java.net.URLDecoder.decode(e.arguments?.getString("name")?:"Room","UTF-8")){navController.popBackStack()}}
composable(Routes.SETTINGS){SettingsScreen{navController.popBackStack()}}
composable(Routes.HELP){HelpScreen{navController.popBackStack()}}
composable(Routes.PROFILE){ProfileScreen{navController.popBackStack()}}
composable(Routes.STATUS){StatusScreen{navController.popBackStack()}}
composable(Routes.HISTORY){HistoryScreen{navController.popBackStack()}}
composable(Routes.REGISTER){RegistrationScreen{navController.popBackStack()}}
composable(Routes.MENUS){LegacyMenusScreen({navController.popBackStack()},{})}
}}