package com.springboot.project.doamin.message;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByUserId(@Param("user_id") Long user_id, Pageable pageable);

    //읽었는지 아닌지 확인
    @Modifying
    @Query(value = "update Message set read_check=true where id =:id", nativeQuery = true)
    void readCheck(@Param("id") Long id) throws Exception;

    @Query("SELECT COUNT(m.id) FROM Message m WHERE user_id =:user_id AND read_check = false")
    int countMessagesByUserRead(@Param("user_id") Long user_id);
}
