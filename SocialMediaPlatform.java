import java.util.*;
import javax.xml.stream.events.Comment;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.TreeSet;

public class SocialMediaPlatform {
    public static void main(String[] args){
        User user1 = new User("Dilara", "B University");
        User user2 = new User("Yılmaz", "Photographer");
        User user3 = new User("Lara", "Blogger");

        user1.follow(user2);
        user1.follow(user3);

        user2.createPost("Hello sunny");
        user3.createPost("Just like a dream");

        user1.addCommentToPost(user2, 0, "Good post, Yılmaz!");
        user1.addToPostFavorites(user3, 1);

        user1.showFeed();

        user1.unfollow(user3);
        user1.showFeed();

        user1.showFavorites();
        user1.removeFromPostFavorites(user3, 1);
        user1.showFavorites();
    }

    static class User {
        private String name;
        private String bio;
        private LinkedHashMap<Integer, Post> posts;
        private HashSet<User> following;
        private TreeSet<Post> favorites;
        private static int postCounter = 0;

        public User(String name, String bio){
            this.name = name;
            this.bio = bio;
            this.posts = new LinkedHashMap<>();
            this.following = new HashSet<>();
            this.favorites = new TreeSet<>();
        }

        public String getName() {
            return name;
        }

        public String getBio() {
            return bio;
        }

        public Post getPost(int id) {
            return posts.get(id);
        }

        public void follow(User user) {
            following.add(user);
            System.out.println(name + " follows " + user.getName());
        }

        public void unfollow(User user) {
            if (following.remove(user)) {
                System.out.println(name + " unfollowed " + user.getName());
            } else {
                System.out.println(name + " does not follow " + user.getName());
            }
        }

        public void createPost(String content) {
            Post newPost = new Post(postCounter++, this, content);
            posts.put(newPost.getId(), newPost);
            System.out.println(name + " published a new post: " + content);
        }

        public void addCommentToPost(User user, int postId, String comment) {
            Post post = user.getPost(postId);
            if (post != null) {
                post.addComment(new Comment(this, comment));
                System.out.println(name + " commented on " + user.getName() + "'s post: " + comment);
            }
        }

        public void addToPostFavorites(User user, int postId) {
            Post post = user.getPost(postId);
            if (post != null) {
                favorites.add(post);
                System.out.println(name + " liked " + user.getName() + "'s post: " + post.getContent());
            }
        }

        public void removeFromPostFavorites(User user, int postId) {
            Post post = user.getPost(postId);
            if (post != null && favorites.remove(post)) {
                System.out.println(name + " removed from favorites " + user.getName() + "'s post: " + post.getContent());
            }
        }

        public void showFeed() {
            System.out.println("\n" + name + "'s Feed:");
            for (User user : following) {
                System.out.println(user.getName() + "'s Posts (" + user.getBio() + "):");
                for (Post post : user.getPosts()) {
                    System.out.println(post);
                }
            }
        }

        public void showFavorites() {
            System.out.println("\n" + name + "'s Favorite Posts:");
            for (Post post : favorites) {
                System.out.println(post);
            }
        }

        public Collection<Post> getPosts() {
            return posts.values();
        }
    }

    static class Post implements Comparable<Post> {
        private int id;
        private User user;
        private String content;
        private List<Comment> comments;

        public Post(int id, User user, String content) {
            this.id = id;
            this.user = user;
            this.content = content;
            this.comments = new ArrayList<>();
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public void addComment(Comment comment) {
            comments.add(comment);
        }

        @Override
        public int compareTo(Post otherPost) {
            return Integer.compare(this.id, otherPost.id);
        }

        @Override
        public String toString() {
            return "Post ID: " + id + " | " + user.getName() + ": " + content + " | Comments: " + comments;
        }
    }

    static class Comment {
        private User user;
        private String text;

        public Comment(User user, String text) {
            this.user = user;
            this.text = text;
        }

        @Override
        public String toString() {
            return user.getName() + ": " + text;
        }
    }
}
