package com.ntt.spring.security.login.security.services.itp;



import com.ntt.spring.security.login.models.dto.LibraryDTO;
import com.ntt.spring.security.login.models.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LibraryService {
    public Object getUser(long id);
    public Object updateWalletLibrary(LibraryDTO dto);
    public Object updateRoleLibrary(Long id);
    public Object updateLibrary(LibraryDTO dto);
    public Object deleteLibrary(long id);
}
