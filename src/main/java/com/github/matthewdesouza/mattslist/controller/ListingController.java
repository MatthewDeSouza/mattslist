package com.github.matthewdesouza.mattslist.controller;

import com.github.matthewdesouza.mattslist.entity.Post;
import com.github.matthewdesouza.mattslist.entity.Role;
import com.github.matthewdesouza.mattslist.entity.Topic;
import com.github.matthewdesouza.mattslist.entity.User;
import com.github.matthewdesouza.mattslist.exception.InvalidPostAccessException;
import com.github.matthewdesouza.mattslist.service.PostService;
import com.github.matthewdesouza.mattslist.service.TopicService;
import com.github.matthewdesouza.mattslist.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Acts as the Controller within the MVC to map models to their respective view, as well as receive data
 * from said views to store within the models.
 * @author Matthew DeSouza
 */
@Slf4j
@NoArgsConstructor
@Controller
public class ListingController {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    private static final String TOPIC = "topic";
    private static final String TOPICS = "topics";
    private static final String POST = "post";
    private static final String DATE = "date";
    private static final String PRIVILEGED = "privileged";
    private TopicService topicService;
    private PostService postService;
    private UserService userService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns the view for all topics within the database.
     * @param model {@link Model}
     * @param principal {@link Principal}
     * @return {@link String}
     */
    @GetMapping("/topics")
    public String getAllTopics(Model model, Principal principal) {
        log.info("Getting all topics");
        List<Topic> topics = topicService.getAllTopics();
        if (topics.isEmpty()) {
            log.warn("There are no topics.");
        }
        model.addAttribute(PRIVILEGED, getPrivilegeStatus(principal));
        model.addAttribute(TOPICS, topics);
        return "topic/all";
    }

    /**
     * Receives input from the View to store within the database.
     * @param topic {@link Topic}
     * @return {@link String}
     */
    @PostMapping("/topics")
    public String saveTopic(@ModelAttribute(TOPIC) Topic topic) {
        log.info("Saving topic (id={}, name={})", topic.getId(), topic.getName());
        if (topicService.getTopicByName(topic.getName()).isEmpty()) {
            topicService.saveTopic(topic);
        }
        return "redirect:/topics";
    }

    /**
     * Returns the view for a singular topic.
     * @param model {@link Model}
     * @param topicName @{@link String}
     * @return {@link String}
     */
    @GetMapping("/topics/{topicName}")
    public String getSingleTopicByName(Model model, @PathVariable String topicName) {
        log.info("Getting single topic (name={})", topicName);
        Topic topic = topicService.getTopicByName(topicName).get(0);
        model.addAttribute(TOPIC, topic);
        return "topic/single";
    }

    /**
     * Returns the view for creating a new topic.
     * @param model {@link Model}
     * @return {@link String}
     */
    @GetMapping("/topics/create")
    public String createTopic(Model model) {
        log.info("New topic creation requested.");
        model.addAttribute(TOPIC, new Topic());
        return "topic/create";
    }

    /**
     * Deletes a topic from the database given its id, and redirects to the view of all topics.
     * @param id {@link Long}
     * @return {@link String}
     */
    @GetMapping("/topics/delete/{id}")
    public String deleteTopic(@PathVariable Long id) {
        log.info("Topic deletion requested for supplied id (id={}).", id);
        Topic topic = topicService.getTopicById(id);
        topicService.deleteTopic(topic);
        return "redirect:/topics";
    }

    /**
     * Returns the view for creating a post for a topic.
     * @param model {@link Model}
     * @param topicName {@link String}
     * @return {@link String}
     */
    @GetMapping("/topics/{topicName}/posts/create")
    public String createPostInTopic(Model model, @PathVariable String topicName) {
        List<Topic> topic = topicService.getTopicByName(topicName);
        model.addAttribute(POST, new Post());
        model.addAttribute(TOPIC, topic);
        return "post/create";
    }

    /**
     * Calls the function to delete a post from the topic. Receives topic from service by id.
     * @param id {@link Long}
     * @return {@link String}
     */
    @GetMapping("/topics/{topicName}/{id}/delete")
    public String deletePostInTopic(@PathVariable Long id) {
        log.info("Post deletion requested for supplied id (id={}).", id);
        Post post = postService.findPostById(id);
        postService.deletePost(post);
        return "redirect:/topics";
    }

    /**
     * Saves a post in a given topic.
     * @param topic {@link Topic}
     * @param post {@link Post}
     * @param principal {@link Principal}
     * @return {@link String}
     */
    @PostMapping("/topics/{name}")
    public String savePostInTopic(@ModelAttribute(TOPIC) Topic topic, @ModelAttribute(POST) Post post, Principal principal) {
        log.info("Saving Topic (id={}, name={}).", topic.getId(), topic.getName());
        User user = userService.getUserByUsername(principal.getName());
        post.setUser(user);
        user.addPost(post);
        userService.saveUser(user);
        topicService.updateTopic(post, topic.getName());
        log.info("Save call completed for Topic.");
        return "redirect:/topics/{name}";
    }

    /**
     * Gets a Post by id from the database, returns it to the view.
     * @param model {@link Model}
     * @param id {@link String}
     * @param principal {@link Principal}
     * @return {@link String}
     * @throws InvalidPostAccessException Thrown if {@link Post} does not belong to {@link Topic}.
     */
    @GetMapping("/topics/{topicName}/posts/{id}")
    public String getSinglePostInTopic(Model model, @PathVariable String id, @PathVariable String topicName, Principal principal) throws InvalidPostAccessException {
        log.info("Getting single Post (id={}) from Topic (name={}).", id, topicName);
        Post post = postService.findPostById(Long.parseLong(id));
        Topic topic = topicService.getTopicByName(post.getTopic().getName()).get(0);

        if (!topic.getName().equals(post.getTopic().getName())) {
            log.error("Error! Post (id={}) does not belong to Topic (name={})!", id, topicName);
            throw new InvalidPostAccessException("Post does not belong to Topic!");
        }
        boolean isAdmin = false;
        if (principal != null) {
            isAdmin = (getPrivilegeStatus(principal) || principal.getName().equals(post.getUser().getUsername()));
        }

        String formattedDate = dateTimeFormatter.format(post.getCreationDate());

        model.addAttribute(DATE, formattedDate);
        model.addAttribute(POST, post);
        model.addAttribute(TOPIC, topic);
        model.addAttribute(PRIVILEGED, isAdmin);

        return "post/single";
    }

    /**
     * This will return whether we are currently authenticated through a user with role ADMIN.
     * @param principal {@link Principal}
     * @return {@link Boolean}
     */
    public boolean getPrivilegeStatus(Principal principal) {
        log.info("Getting privilege status.");
        if (principal == null) {
            return false;
        }
        User user = userService.getUserByUsername(principal.getName());
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
