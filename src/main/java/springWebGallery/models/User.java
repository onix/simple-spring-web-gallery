package springWebGallery.models;

import springWebGallery.models.Exceptions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum UserRoles {
    ADMIN, USER
}

public class User {
    private static final String LOGIN_REGEX_PATTERN = "^[a-zA-Z0-9_-]{2,}$";
    private static final int minLoginLength = 2; // Or include to regex??/ x2
    private static final int maxLoginLength = 50;

    private static final String SHA_256_REGEX_PATTERN = "[A-Fa-f0-9]{64}";
    private static final int minPasswordLength = 6;
    private static final int maxPasswordLength = 100;

    private static final String EMAIL_REGEX_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final int minEmailLength = 5;
    private static final int maxEmailLength = 50;

    private static final int minNameLength = 1;
    private static final int maxNameLength = 50;

    private static final int minSurnameLength = 1;
    private static final int maxSurnameLength = 50;

    private static final int minSecondNameLength = 1;
    private static final int maxSecondNameLength = 50;

    private int idUser;
    private String login;
    private String passwordHash;
    private String email;
    private String name;
    private String surname;
    private String secondName;
    private UserRoles role;

    public User(String login, String password, String email) throws NoSuchAlgorithmException {
        this.setLoginAndValidate(login);
        setPasswordAndCheckIsHash(password);
        this.setEmailAndValidate(email);
        this.setRoleAndValidate(UserRoles.USER.toString());
    }

    public User(int idUser, String login, String passwordHash, String email, String name, String surname, String secondName, String role) {
        this.idUser = idUser;
        this.setLoginAndValidate(login);
        this.setPasswordAndCheckIsHash(passwordHash);
        this.setEmailAndValidate(email);
        this.setNameAndValidate(name);
        this.setSurnameAndValidate(surname);
        this.setSecondNameAndValidate(secondName);
        this.setRoleAndValidate(role);
    }

    public int getIdUser() {
        return idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLoginAndValidate(final String login) {
        if (login != null) {
            if (login.length() >= minLoginLength && login.length() <= maxLoginLength) {
                Pattern pattern = Pattern.compile(LOGIN_REGEX_PATTERN);
                Matcher matcher = pattern.matcher(login);
                if(matcher.matches()) {
                    this.login = login;
                } else throw new WrongFieldFormat("Login field has not valid. A-Z, a-z, 0-9, - and _ symbols are allowed.");
            } else throw new WrongFieldSizeException("Login field must be from " + minLoginLength + " to " + maxLoginLength + " characters long.");
        } else throw new NullOrEmptyFieldException("Login field is empty or has a null link.");
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Checks is input string a SHA-256 hash. If not, interpretates input as password string and hashes it. If is,
     * saves in model without changes.
     *
     * @param passwordOrHash Is a password input string or hashed password string. This method determines what string is input automatically.
     */
    public void setPasswordAndCheckIsHash(final String passwordOrHash) {
        if (passwordOrHash != null) {
            if (passwordOrHash.length() >= minPasswordLength && passwordOrHash.length() <= maxPasswordLength) {
                Pattern pattern = Pattern.compile(SHA_256_REGEX_PATTERN);
                Matcher matcher = pattern.matcher(passwordOrHash);
                if(matcher.matches()) {
                    this.passwordHash = passwordOrHash;
                } else
                    this.passwordHash = hashPassword(passwordOrHash);
            } else throw new WrongFieldSizeException("Password field must be from " + minPasswordLength + " to " + maxPasswordLength + " characters long.");
        } else throw new NullOrEmptyFieldException("Password field is empty or has a null link.");
    }

    public String getEmail() {
        return email;
    }

    public void setEmailAndValidate(final String email) {
        if (email != null) {
            if (email.length() >= minEmailLength && email.length() <= maxEmailLength) {
                Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);
                Matcher matcher = pattern.matcher(email);
                if(matcher.matches()) {
                    this.email = email;
                } else throw new WrongFieldFormat("Email field has not valid email address.");
            } else throw new WrongFieldSizeException("Email field must be from " + minEmailLength + " to " + maxEmailLength + " characters long.");
        } else throw new NullOrEmptyFieldException("Email field is empty or has a null link.");
    }

    public String getName() {
        return name;
    }

    public void setNameAndValidate(final String name) {
        if (name != null) {
            if (name.length() >= minNameLength && name.length() <= maxNameLength) {
                this.name = name;
            } else throw new WrongFieldSizeException("Name field must be from " + minNameLength + " to " + maxNameLength + " characters long.");
        } else {
            this.name = null;
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurnameAndValidate(final String surname) {
        if (surname != null) {
            if (surname.length() >= minSurnameLength && surname.length() <= maxSurnameLength) {
                this.surname = surname;
            } else throw new WrongFieldSizeException("Surname field must be from " + minSurnameLength + " to " + maxSurnameLength + " characters long.");
        } else {
            this.surname = null;
        }
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondNameAndValidate(final String secondName) {
        if (secondName != null) {
            if (secondName.length() >= minSecondNameLength && secondName.length() <= maxSecondNameLength) {
                this.secondName = secondName;
            } else throw new WrongFieldSizeException("Second name field must be from " + minSecondNameLength + " to " + maxSecondNameLength + " characters long.");
        } else {
            this.secondName = null;
        }
    }

    public String getRole() {
        return role.toString();
    }

    public void setRoleAndValidate(final String role) {
        if (role != null) {
            for (UserRoles r : UserRoles.values()) {
                if (r.toString().toLowerCase().equals(role.toLowerCase())) {
                    this.role = r;
                    return;
                }
            }
            throw new RoleDoesNotExistException("Such role " + role +" does not exist.");
        } else throw new NullOrEmptyFieldException("Role field is empty or has a null link.");
    }

    private String hashPassword(final String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());

            byte byteData[] = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordHashingException();
        }
    }
}
