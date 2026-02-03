package umc.study.repository.StoreRepository;

import umc.study.domain.Store;
import java.util.*;
public interface StoreRepositoryCustom{
    List<Store> dynamicQueryWithBooleanBuilder(String name, Float score);
}
