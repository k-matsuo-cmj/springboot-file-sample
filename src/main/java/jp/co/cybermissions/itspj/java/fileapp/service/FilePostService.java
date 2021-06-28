package jp.co.cybermissions.itspj.java.fileapp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jp.co.cybermissions.itspj.java.fileapp.model.FilePost;
import jp.co.cybermissions.itspj.java.fileapp.model.FilePostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilePostService {

    private final FilePostRepository rep;

    @Value("${file-app.filedir}")
    private String filedir;

    @Transactional
    public FilePost store(MultipartFile file, FilePost filePost) throws IOException {
        String imageName = StringUtils.cleanPath(file.getOriginalFilename());
        filePost.setImageName(imageName);

        // データベース保存
        FilePost db = rep.save(filePost);
        // ファイル保存
        storeStorage(db.getId(), file);

        return db;
    }

    private void storeStorage(Long id, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File dir = makeIdDir(id); // 保存するディレクトリを作成
        Path path = Paths.get(dir.getAbsolutePath(), fileName);
        Files.write(path, file.getBytes());
    }

    private File makeIdDir(Long id) {
        File dir = new File(filedir, String.valueOf(id));
        if (!dir.exists())
            dir.mkdirs();
        return dir;
    }

    public String getFileDir() {
        return filedir;
    }
}
