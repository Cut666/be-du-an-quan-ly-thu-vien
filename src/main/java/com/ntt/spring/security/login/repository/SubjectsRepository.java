package com.ntt.spring.security.login.repository;



import com.ntt.spring.security.login.models.entity.Subjectenum;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.Sub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectsRepository extends JpaRepository<Subjects,Integer> {

    Subjects findByPhone(String phone);


    Subjects findByGmail(String gmail);

    @Query("SELECT s FROM Subjects s JOIN s.subjectenums se WHERE se.sub = :sub AND s.CCCD = :CCCD")
    public List<Subjects> findSubjectsBySubjectenumSubAndCCCD(@Param("sub") Sub sub, @Param("CCCD") String CCCD);
    @Query("SELECT s FROM Subjects s JOIN s.subjectenums se JOIN s.user u WHERE se.sub = :sub AND u.id = :userId AND s.CCCD = :cccd")
    public List<Subjects> findSubjectsBySubjectEnumAndUserAndCCCD(@Param("sub") Sub sub, @Param("userId") Long userId, @Param("cccd") String CCCD);

    @Query("SELECT s FROM Subjects s JOIN s.subjectenums se JOIN s.user u WHERE se.sub = :sub AND u.id = :userId AND s.taxcode = :taxcode")
    public List<Subjects> findSubjectsBySubjectEnumAndUserAndTaxcode(@Param("sub") Sub sub, @Param("userId") Long userId, @Param("taxcode") String taxcode);
    @Query("SELECT s FROM Subjects s JOIN s.subjectenums se JOIN s.user u WHERE se.sub = :sub AND u.id = :userId")
    public List<Subjects> findSubjectsBySubjectEnumAndUser(@Param("sub") Sub sub, @Param("userId") Long userId);

    Subjects findByCCCD(String cccd);

    List<Subjects> findBySubjectenumsIn(List<Subjectenum> asList);

    Subjects findByTaxcode(String taxcode);
    @Query("SELECT s FROM Subjects s WHERE s.user.id = :userId AND (s.CCCD = :code OR s.taxcode = :code)")
    Subjects findByUserIdAndCode(@Param("userId") Long userId, @Param("code") String code);

    List<Subjects> findSubjectsByUser(User user1);

    Subjects findSubjectsByCCCDAndUser(String cccd, User user);

    Subjects findSubjectsByPhoneAndUser(String phone, User user);

    Subjects findSubjectsByTaxcodeAndUser(String taxcode, User user);
}
