package jp.co.cybermissions.itspj.java.fileapp.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.cybermissions.itspj.java.fileapp.model.BlobPost;
import jp.co.cybermissions.itspj.java.fileapp.model.BlobPostRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BlobPostController {

  private final BlobPostRepository rep;

  @GetMapping("")
  public String index(Model model) {
    model.addAttribute("posts", rep.findAll(Sort.by(Direction.DESC, "createdAt")));
    return "blob/index";
  }

  @GetMapping("/new")
  public String newPost(@ModelAttribute BlobPost blobPost) {
    return "blob/new";
  }

  @PostMapping("")
  public String create(@RequestParam("file") MultipartFile file, @Validated @ModelAttribute BlobPost blobPost,
      BindingResult result) {
    if (result.hasErrors()) {
      return "blob/new";
    }
    if (file != null) {
      try {
        blobPost.setImage(file.getBytes());
        blobPost.setType(file.getContentType());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    blobPost.setCreatedAt(LocalDateTime.now());
    rep.save(blobPost);
    return "redirect:/";
  }

}
