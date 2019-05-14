package com.romanidze.metrikano.clickhouseservice.repositories.implementations;

import com.romanidze.metrikano.clickhouseservice.domain.VKUser;
import com.romanidze.metrikano.clickhouseservice.repositories.interfaces.VKUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * 08.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Repository
public class VKUserRepositoryImpl implements VKUserRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_QUERY = "SELECT * FROM metrikano_db.vk_users";
    private static final String INSERT_QUERY =
            "INSERT INTO metrikano_db.vk_users(id, username, user_id, user_type, first_name, last_name, is_closed, can_access_closed, " +
                    "city_id, city_title, country_id, country_title, has_photo, online, can_post, last_seen_time, " +
                    "last_seen_platform, counters_photos, counters_friends, creation_time) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_RECORD_ID_QUERY =
            "SELECT * FROM metrikano_db.vk_users WHERE id = ?";
    private static final String UPDATE_QUERY =
            "ALTER TABLE metrikano_db.vk_users UPDATE username=?, user_type=?, first_name=?, last_name=?, is_closed=?, can_access_closed=?, " +
                    "city_id=?, city_title=?, country_id=?, country_title=?, has_photo=?, online=?, can_post=?, " +
                    "last_seen_time=?, platform=?, photo_counter=?, friend_counter=?, creation_time=? " +
                    "WHERE user_id=?";
    private static final String DELETE_QUERY =
            "ALTER TABLE metrikano_db.vk_users DELETE WHERE user_id=?";
    private static final String FIND_ALL_RECORDS_BY_IDS =
            "SELECT * FROM metrikano_db.vk_users WHERE id IN(:ids)";

    private RowMapper<VKUser> vkUserRowMapper = (resultSet, rowNumber) ->

            VKUser.builder()
                  .id(resultSet.getString("id"))
                  .username(resultSet.getString("username"))
                  .userID(resultSet.getLong("user_id"))
                  .userType(resultSet.getString("user_type"))
                  .firstName(resultSet.getString("first_name"))
                  .lastName(resultSet.getString("last_name"))
                  .isClosed(resultSet.getShort("is_closed") != 0)
                  .canAccessClosed(resultSet.getShort("can_access_closed") != 0)
                  .cityID(resultSet.getLong("city_id"))
                  .cityTitle(resultSet.getString("city_title"))
                  .countryID(resultSet.getLong("country_id"))
                  .countryTitle(resultSet.getString("country_title"))
                  .hasPhoto(resultSet.getShort("has_photo"))
                  .online(resultSet.getShort("online"))
                  .canPost(resultSet.getShort("can_post"))
                  .lastSeenTime(resultSet.getLong("last_seen_time"))
                  .platform(resultSet.getShort("last_seen_platform"))
                  .photoCounter(resultSet.getLong("counters_photos"))
                  .friendCounter(resultSet.getLong("counters_friends"))
                  .creationTime(resultSet.getString("creation_time"))
                  .build();

    @Autowired
    public VKUserRepositoryImpl(@Qualifier("clickHouseJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<VKUser> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_QUERY, this.vkUserRowMapper);
    }

    @Override
    public void save(VKUser model) {

        this.jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);

            ps.setString(1, model.getId());
            ps.setString(2, model.getUsername());
            ps.setLong(3, model.getUserID());
            ps.setString(4, model.getUserType());
            ps.setString(5, model.getFirstName());
            ps.setString(6, model.getLastName());
            ps.setShort(7, model.getIsClosed() ? (short)1 : (short)0);
            ps.setShort(8, model.getCanAccessClosed() ? (short)1 : (short)0);
            ps.setLong(9, model.getCityID());
            ps.setString(10, model.getCityTitle());
            ps.setLong(11, model.getCountryID());
            ps.setString(12, model.getCountryTitle());
            ps.setShort(13, model.getHasPhoto());
            ps.setShort(14, model.getOnline());
            ps.setShort(15, model.getCanPost());
            ps.setLong(16, model.getLastSeenTime());
            ps.setShort(17, model.getPlatform());
            ps.setLong(18, model.getPhotoCounter());
            ps.setLong(19, model.getFriendCounter());
            ps.setString(20, model.getCreationTime());

            return ps;

        });

    }

    @Override
    public VKUser find(String id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_RECORD_ID_QUERY, this.vkUserRowMapper, id);
    }

    @Override
    public void delete(String id) {
        this.jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public void update(VKUser model) {
        this.jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
            ps.setString(1, model.getUsername());
            ps.setString(2, model.getUserType());
            ps.setString(3, model.getFirstName());
            ps.setString(4, model.getLastName());
            ps.setShort(5, model.getIsClosed() ? (short)1 : (short)0);
            ps.setShort(6, model.getCanAccessClosed() ? (short)1 : (short)0);
            ps.setLong(7, model.getCityID());
            ps.setString(8, model.getCityTitle());
            ps.setLong(9, model.getCountryID());
            ps.setString(10, model.getCountryTitle());
            ps.setShort(11, model.getHasPhoto());
            ps.setShort(12, model.getOnline());
            ps.setShort(13, model.getCanPost());
            ps.setLong(14, model.getLastSeenTime());
            ps.setShort(15, model.getPlatform());
            ps.setLong(16, model.getPhotoCounter());
            ps.setLong(17, model.getFriendCounter());
            ps.setString(18, model.getCreationTime());
            ps.setLong(19, model.getUserID());
            return ps;

        });
    }

    @Override
    public List<VKUser> getRecordsByIDs(List<String> ids) {

        Map<String, List<String>> paramMap = Map.ofEntries(
                Map.entry("ids", ids)
        );

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());

        return template.query(FIND_ALL_RECORDS_BY_IDS, paramMap, this.vkUserRowMapper);
    }
}
