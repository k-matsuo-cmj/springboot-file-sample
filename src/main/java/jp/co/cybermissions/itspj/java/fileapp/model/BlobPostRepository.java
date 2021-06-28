package jp.co.cybermissions.itspj.java.fileapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlobPostRepository extends JpaRepository<BlobPost, Long> {

}
