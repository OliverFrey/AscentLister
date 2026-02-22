import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ascentlister.route.domain.Route
import com.example.ascentlister.route.presentation.route_list.RouteListAction
import com.example.ascentlister.route.presentation.route_list.RouteListState
import com.example.ascentlister.route.presentation.route_list.RouteListViewModel
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

}

