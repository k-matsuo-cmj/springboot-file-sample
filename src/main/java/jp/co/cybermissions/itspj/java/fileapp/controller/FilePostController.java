package jp.co.cybermissions.itspj.java.fileapp.controller;

import java.io.IOException;
import java.time.LocalDateTime;

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

import jp.co.cybermissions.itspj.java.fileapp.model.FilePost;
import jp.co.cybermissions.itspj.java.fileapp.model.FilePostRepository;
import jp.co.cybermissions.itspj.java.fileapp.service.FilePostService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilePostController {
  private final FilePostRepository rep;
  private final FilePostService service;

  @GetMapping("")
  public String index(Model m) {
    m.addAttribute("files", rep.findAll());
    m.addAttribute("filedir", service.getFileDir());
    return "file/index";
  }

  @GetMapping("/new")
  public String newPost(@ModelAttribute FilePost filePost) {
    return "file/new";
  }

  @PostMapping("")
  public String create(@RequestParam("file") MultipartFile file, @Validated @ModelAttribute FilePost filePost,
      BindingResult result) throws IOException {
    if (result.hasErrors()) {
      return "file/new";
    }
    filePost.setCreatedAt(LocalDateTime.now());
    service.store(file, filePost);
    return "redirect:/files";
  }

}
