package com.cyanideph.java.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.cyanideph.java.ui.screens.*
object Routes{const val LOGIN="login";const val REGISTER="register";const val MAIN="main";const val BUDDIES="buddies";const val MESSAGES="messages";const val ROOMS="rooms";const val ROOM="room/{name}";const val SETTINGS="settings";const val HELP="help";const val PROFILE="profile";const val STATUS="status";const val HISTORY="history";const val MENUS="menus";const val RECEIVED_CONTACTS="received-contacts";const val BATTERY="battery";const val OFFLINE="offline";const val PURCHASE_HISTORY="purchase-history";const val CHANGE_MOBILE="change-mobile";const val STORED_MESSAGE="stored-message";const val CHAT_INVITE="chat-invite/{name}";const val PASSWORD="password";const val CHATROOM_TONES="chatroom-tones";const val SUBSCRIPTION="subscription"}
@Composable fun UzzapNavHost(navController:NavHostController){NavHost(navController,startDestination=Routes.LOGIN){
composable(Routes.LOGIN){LoginScreen{navController.navigate(Routes.MAIN){popUpTo(Routes.LOGIN){inclusive=true}}}}
composable(Routes.MAIN){MainMenuScreen({navController.navigate(Routes.BUDDIES)},{navController.navigate(Routes.MESSAGES)},{navController.navigate(Routes.ROOMS)},{navController.navigate(Routes.SETTINGS)},{navController.navigate(Routes.HELP)})}
composable(Routes.BUDDIES){BuddyListScreen{navController.popBackStack()}}
composable(Routes.RECEIVED_CONTACTS){ReceivedContactsScreen{navController.popBackStack()}}
composable(Routes.MESSAGES){MessagesScreen(onBack={navController.popBackStack()},onViewHistory={navController.navigate(Routes.HISTORY)},onReceivedContacts={navController.navigate(Routes.RECEIVED_CONTACTS)},onProfile={navController.navigate(Routes.PROFILE)})}
composable(Routes.ROOMS){ChatRoomsScreen({navController.popBackStack()}){n->navController.navigate("room/"+java.net.URLEncoder.encode(n,"UTF-8"))}}
composable(Routes.ROOM){e->ChatRoomScreen(java.net.URLDecoder.decode(e.arguments?.getString("name")?:"Room","UTF-8")){navController.popBackStack()}}
composable(Routes.SETTINGS){SettingsScreen(onBack={navController.popBackStack()},onProfile={navController.navigate(Routes.PROFILE)},onStatus={navController.navigate(Routes.STATUS)},onPassword={navController.navigate(Routes.PASSWORD)},onMobile={navController.navigate(Routes.CHANGE_MOBILE)},onOffline={navController.navigate(Routes.OFFLINE)},onChatroomTones={navController.navigate(Routes.CHATROOM_TONES)})}
composable(Routes.HELP){HelpScreen{navController.popBackStack()}}
composable(Routes.PROFILE){ProfileScreen{navController.popBackStack()}}
composable(Routes.STATUS){StatusScreen{navController.popBackStack()}}
composable(Routes.HISTORY){HistoryScreen{navController.popBackStack()}}
composable(Routes.REGISTER){RegistrationScreen{navController.popBackStack()}}
composable(Routes.MENUS){LegacyMenusScreen({navController.popBackStack()},{})}
composable(Routes.BATTERY){BatterySavingScreen{navController.popBackStack()}}
composable(Routes.OFFLINE){OfflineSettingsScreen{navController.popBackStack()}}
composable(Routes.PURCHASE_HISTORY){PurchaseHistoryScreen{navController.popBackStack()}}
composable(Routes.CHANGE_MOBILE){ChangeMobileScreen{navController.popBackStack()}}
composable(Routes.STORED_MESSAGE){StoredMessageScreen{navController.popBackStack()}}
composable(Routes.CHAT_INVITE){e->ChatInviteScreen(java.net.URLDecoder.decode(e.arguments?.getString("name")?:"Room","UTF-8")){navController.popBackStack()}}
composable(Routes.PASSWORD){ChangePasswordScreen{navController.popBackStack()}}
composable(Routes.CHATROOM_TONES){ChatroomTonesScreen{navController.popBackStack()}}
composable(Routes.SUBSCRIPTION){SubscriptionMenuScreen({navController.popBackStack()},{navController.navigate(Routes.PURCHASE_HISTORY)})}
}}