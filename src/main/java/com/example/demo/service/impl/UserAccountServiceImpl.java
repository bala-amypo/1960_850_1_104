public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository repository;

    public UserAccountServiceImpl(UserAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserAccount createUser(UserAccount user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
        // no hashing yet
        return repository.save(user);
    }

    @Override
    public UserAccount authenticate(String email, String password) {
        UserAccount user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new BadRequestException("User is not active");
        }
        if (!password.equals(user.getPasswordHash())) {
            throw new BadRequestException("Invalid credentials");
        }
        return user;
    }

    // other methods unchanged
}
