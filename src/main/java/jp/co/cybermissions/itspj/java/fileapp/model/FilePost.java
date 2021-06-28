package jp.co.cybermissions.itspj.java.fileapp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_posts")
@Getter
@Setter
@RequiredArgsConstructor
public class FilePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 1000)
    private String description;

    /** 画像のファイル名 */
    private String imageName;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime createdAt;

}
