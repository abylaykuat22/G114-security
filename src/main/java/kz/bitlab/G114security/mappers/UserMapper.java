package kz.bitlab.G114security.mappers;

import java.util.List;
import kz.bitlab.G114security.dto.UserCreate;
import kz.bitlab.G114security.dto.UserUpdate;
import kz.bitlab.G114security.dto.UserView;
import kz.bitlab.G114security.models.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(source = "email", target = "userEmail")
  @Mapping(source = "fullName", target = "name")
  UserView toView(User user);

  List<UserView> toViewList(List<User> entities);

  User toEntity(UserView dto);

  @Mapping(source = "customEmail", target = "email")
  @Mapping(source = "middleName", target = "fullName")
  User toEntity(UserCreate dto);

  User toEntity(UserUpdate dto);
}
