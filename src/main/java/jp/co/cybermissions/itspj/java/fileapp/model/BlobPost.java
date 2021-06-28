package jp.co.cybermissions.itspj.java.fileapp.model;

import java.time.LocalDateTime;
import java.util.Base64;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

/**
 * 画像データをDBに直接格納するエンティティ
 */
@Getter
@Setter
@Entity
@Table(name = "blob_posts")
public class BlobPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Size(max = 1000)
  private String description;

  /** 画像データ */
  @Lob
  private byte[] image;
  /** 画像データ形式 */
  private String type;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime createdAt;

  /**
   * 画像データをHTMLのimageタグで表示可能な形式で取得する
   * 
   * @return BASE64形式文字列
   * @throws Exception
   */
  public String getImageSource() throws Exception {
    StringBuffer sb = new StringBuffer();
    if (image.length > 0) {
      sb.append("data:");
      sb.append(type == null ? "image/png" : type);
      sb.append(";base64,");
      sb.append(Base64.getEncoder().encodeToString(image));
    }
    return sb.toString();
  }
}
