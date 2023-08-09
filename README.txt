Создаем:
 • модель User который имплементируется от UserDetails
 • модель Role который имплементируется от GrantedAuthority
 • класс UserService, который имплементируется от UserDetailsService
 • UserRepository, в котором будет запрос в базу для поиска по email
 • SecurityConfig, можем скопировать класс