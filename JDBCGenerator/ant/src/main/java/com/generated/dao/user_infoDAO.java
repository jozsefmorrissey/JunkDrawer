package com.generated.dao;

import com.generated.bean.user_info;
import java.lang.String;
import java.util.List;

public interface user_infoDAO {
  List<user_info> getUserByEmail(String email);
}
