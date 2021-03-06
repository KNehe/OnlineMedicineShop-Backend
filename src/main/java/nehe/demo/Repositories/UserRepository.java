package nehe.demo.Repositories;

import nehe.demo.Modals.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {

    User findByEmail(String email);


    @Query(value = "SELECT id FROM myusers WHERE email=?1",nativeQuery = true)
    Optional<Integer> findUserId(String email);

    @Query(value = "SELECT firstname FROM myusers WHERE email=?1",nativeQuery = true)
    Optional<String> findFirstName(String email);
    
    @Modifying
    @Query(value="UPDATE myusers set password=? where id=?",nativeQuery = true)
    int changePassword(String newPassword,int userId);

    @Query(value = "SELECT email FROM myusers WHERE id=?1",nativeQuery = true)
    String findUserEmail(int id);

    //find email by email
    @Query(value = "SELECT email FROM myusers WHERE email=?1",nativeQuery = true)
    String findEmail(String email);

    @Modifying
    @Query(value="UPDATE myusers set firstname=? where id=?",nativeQuery = true)
    int changeFirstName(String firstName,int id);

    @Modifying
    @Query(value="UPDATE myusers set lastname=? where id=?",nativeQuery = true)
    int changeLastName(String lastName,int id);

    @Modifying
    @Query(value="UPDATE myusers set email=? where id=?",nativeQuery = true)
    int changeEmail(String email,int id);

    @Modifying
    @Query(value="UPDATE myusers set phone=? where id=?",nativeQuery = true)
    int changePhone(String phone, int id);





}
