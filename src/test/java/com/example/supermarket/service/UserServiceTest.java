package com.example.supermarket.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.supermarket.entity.User;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserService userService;

    @Test
    void testFindByOperatorCode() {

        // GIVEN
        User user = new User();
        String operatorCode = "code";
        user.setOperatorCode(operatorCode);

        // WHEN
        when(userService.findByOperatorCode(operatorCode)).thenReturn(user);
        User ret = userService.findByOperatorCode(operatorCode);

        // VERIFY
        assertEquals(ret.getOperatorCode(), user.getOperatorCode());
        verify(userService, times(1)).findByOperatorCode(operatorCode);

    }

    @Test
    void testFindByName() {

        // GIVEN
        String name = "Nome";
        List<User> users = List.of(
                new User("Codice-1", name, "Cognome", "test@email.com", "ruolo", "password"),
                new User("Codice-2", name, "Cognome", "test@email.com", "Ruolo", "password"));

        // WHEN
        when(userService.findByName(name)).thenReturn(users);
        List<User> ret = userService.findByName(name);

        // VERIFY
        assertNotNull(ret);
        assertEquals(2, ret.size());
        assertEquals(name, ret.get(0).getName());
        assertEquals(name, ret.get(1).getName());
        verify(userService, times(1)).findByName(name);

    }

    @Test
    void testFindBySurname() {

        // GIVEN
        String surname = "Cognome";
        List<User> users = List.of(
                new User("Codice-1", "Nome", surname, "test@email.com", "ruolo", "password"),
                new User("Codice-2", "Nome", surname, "test@email.com", "Ruolo", "password"));

        // WHEN
        when(userService.findBySurname(surname)).thenReturn(users);
        List<User> ret = userService.findBySurname(surname);

        // VERIFY
        assertNotNull(ret);
        assertEquals(2, ret.size());
        assertEquals(surname, ret.get(0).getSurname());
        assertEquals(surname, ret.get(1).getSurname());
        verify(userService, times(1)).findBySurname(surname);

    }

    @Test
    void testFindByRole() {

        // GIVEN
        String role = "Ruolo";
        List<User> users = List.of(
                new User("Codice-1", "Nome", "Cognome", "test@email.com", role, "password"),
                new User("Codice-2", "Nome", "Cognome", "test@email.com", role, "password"));

        // WHEN
        when(userService.findByRole(role)).thenReturn(users);
        List<User> ret = userService.findByRole(role);

        // VERIFY
        assertNotNull(ret);
        assertEquals(2, ret.size());
        assertEquals(role, ret.get(0).getRole());
        assertEquals(role, ret.get(1).getRole());
        verify(userService, times(1)).findByRole(role);

    }

    @Test
    void testFindAll() {

        // GIVEN
        List<User> users = List.of(
                new User("Codice-1", "Nome", "Cognome", "test@email.com", "ruolo",
                        "password"),
                new User("Codice-2", "Nome", "Cognome", "test@email.com", "Ruolo",
                        "password"));

        // WHEN
        when(userService.findAll()).thenReturn(users);
        List<User> ret = userService.findAll();

        // VERIFY
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(userService, times(1)).findAll();

    }

    @Test
    void testDeleteAll() {

        List<User> users = List.of(
                new User("Codice-1", "Nome", "Cognome", "test@email.com", "ruolo", "password"),
                new User("Codice-2", "Nome", "Cognome", "test@email.com", "ruolo", "password"));

        when(userService.findAll()).thenReturn(users);
        userService.deleteAll();
        verify(userService, times(1)).deleteAll();
        when(userService.findAll()).thenReturn(null);

        Iterable<User> deletedUsers = userService.findAll();
        assertNull(deletedUsers);

    }

    @Test
    void testDeleteById() {

        // GIVEN
        String operatorCode = "Codice-1";
        User user = new User(operatorCode, "Nome", "Cognome", "test@email.com", "ruolo", "password");

        // WHEN verifica che l'utente venga cancellato
        when(userService.findByOperatorCode(operatorCode)).thenReturn(user);
        userService.deleteByOperatorCode(operatorCode);
        verify(userService, times(1)).deleteByOperatorCode(operatorCode);
        when(userService.findByOperatorCode(operatorCode)).thenReturn(null);

        User deleteUser = userService.findByOperatorCode(operatorCode);
        assertNull(deleteUser);
    }

}
