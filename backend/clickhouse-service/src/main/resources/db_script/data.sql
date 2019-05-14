CREATE DATABASE metrikano_db;

CREATE TABLE metrikano_db.vk_users
(
    id String,
    username String,
    user_id UInt64,
    user_type String,
    first_name String,
    last_name String,
    is_closed UInt8,
    can_access_closed UInt8,
    city_id UInt64,
    city_title String,
    country_id UInt64,
    country_title String,
    has_photo UInt8,
    online UInt8,
    can_post UInt8,
    last_seen_time UInt64,
    last_seen_platform UInt8,
    counters_photos UInt64,
    counters_friends UInt64,
    creation_time String
)
ENGINE = MergeTree()
ORDER BY (user_id, counters_photos, counters_friends);
