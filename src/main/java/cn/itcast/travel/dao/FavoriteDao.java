package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    Favorite findFavoriteByUidAndRid(int uid, int rid);
    int findFavoriteCountByRid(int rid);
    void addFavorite(int uid,int rid);
}
