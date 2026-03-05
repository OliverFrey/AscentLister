import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ascentlister.route.domain.Route
import com.example.ascentlister.route.presentation.route_list.RouteListAction
import com.example.ascentlister.route.presentation.route_list.RouteListState
import com.example.ascentlister.route.presentation.route_list.RouteListViewModel
import com.example.ascentlister.route.presentation.route_list.components.RouteSearchBar
import com.plcoding.bookpedia.core.presentation.DarkBlue
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RouteListScreenRoot(
    viewModel: RouteListViewModel = koinViewModel(),
    onRouteClicked: (Route) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RouteListScreen(
        state = state,
        onAction = { action ->
            when (action){
                is RouteListAction.OnRouteClicked -> onRouteClicked(action.route)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RouteListScreen(
    state: RouteListState,
    onAction: (RouteListAction) -> Unit
){
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        RouteSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(RouteListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

