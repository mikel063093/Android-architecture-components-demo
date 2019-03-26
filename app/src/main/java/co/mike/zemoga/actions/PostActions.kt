package co.mike.zemoga.actions

import co.mike.zemoga.models.Post

sealed class PostActions {
    class ShowLoading(val loading: Boolean) : PostActions()
    class ShowError(val error: String) : PostActions()
    class ShowPosts(val posts: List<Post>) : PostActions()
    class ShowFavortePosts(val posts: List<Post>) : PostActions()
    object ClearView : PostActions()
    object ClickDeleteAll : PostActions()
    object ClickSync : PostActions()
}