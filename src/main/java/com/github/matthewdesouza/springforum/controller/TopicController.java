package com.github.matthewdesouza.springforum.controller;

import com.github.matthewdesouza.springforum.entity.Post;
import com.github.matthewdesouza.springforum.entity.Topic;
import com.github.matthewdesouza.springforum.service.PostService;
import com.github.matthewdesouza.springforum.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TopicController {
    private final TopicService topicService;

    @Autowired
    private PostService postService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public String getAllTopics(Model model) {
        model.addAttribute("topics", topicService.getAllTopics());
        return "topic/all";
    }

    @PostMapping("/topics")
    public String saveTopic(@ModelAttribute("topic") Topic topic) {
        topicService.saveTopic(topic);
        return "redirect:/topics";
    }

    @GetMapping("/topics/{topicName}")
    public String getSingleTopicByName(Model model, @PathVariable String topicName) {
        Topic topic = topicService.getTopicByName(topicName);
        model.addAttribute("topic", topic);
        return "topic/single";
    }

    @GetMapping("/topics/create")
    public String createTopic(Model model) {
        model.addAttribute("topic", new Topic());
        return "topic/create";
    }

    @GetMapping("/topics/{topicName}/posts/create")
    public String createPostInTopic(Model model, @PathVariable String topicName) {
        Topic topic = topicService.getTopicByName(topicName);
        model.addAttribute("post", new Post());
        model.addAttribute("topic", topic);
        return "post/create";
    }

    @PostMapping("/topics/{name}")
    public String savePostInTopic(@ModelAttribute("topic") Topic topic, @ModelAttribute("post") Post post) {
        topic.addPost(post);
        topicService.saveTopic(topic);
        return "redirect:/topics/{name}";
    }

}
