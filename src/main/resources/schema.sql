-- Category
CREATE TABLE IF NOT EXISTS category (
    id      BIGINT PRIMARY KEY NOT NULL,
    title   VARCHAR(50) NOT NULL
);

-- Member
CREATE TABLE IF NOT EXISTS  member (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    email           VARCHAR(255)    NOT NULL UNIQUE,
    password        VARCHAR(255)    NOT NULL,
    nickname        VARCHAR(255)    NOT NULL,
    phone_num       VARCHAR(255)    NOT NULL,
    create_at       TIMESTAMP       NOT NULL,
    update_at       TIMESTAMP       NOT NULL,
    role            VARCHAR(50)     NOT NULL,
    activation      BOOLEAN         NOT NULL,
    profile_img_url VARCHAR(255)
);

-- Follow
CREATE TABLE IF NOT EXISTS follow (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    follower_id     BIGINT,
    following_id    BIGINT,

    CONSTRAINT fk_follow_follower_id FOREIGN KEY (follower_id) REFERENCES member (id),
    CONSTRAINT fk_follow_following_id FOREIGN KEY (following_id) REFERENCES member (id)
);

-- Image
CREATE TABLE IF NOT EXISTS image (
    id  BIGINT PRIMARY KEY,
    url VARCHAR(255)
);

-- Post
CREATE TABLE IF NOT EXISTS post (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NOT NULL,
    price       INT NOT NULL default 0,
    views       INT CHECK ( views >= 0 ) NOT NULL,
    create_at   TIMESTAMP NOT NULL,
    update_at   TIMESTAMP NOT NULL,

    CONSTRAINT fk_post_member_id FOREIGN KEY (member_id) REFERENCES member (id)
);

-- Post Image
CREATE TABLE IF NOT EXISTS post_image (
    image_id  BIGINT NOT NULL,
    post_id BIGINT NOT NULL,

    PRIMARY KEY (image_id, post_id),
    CONSTRAINT fk_post_image_image_id FOREIGN KEY (image_id) REFERENCES image (id),
    CONSTRAINT fk_post_image_post_id FOREIGN KEY (post_id) REFERENCES post (id)
);

-- Interest
CREATE TABLE IF NOT EXISTS interest (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    post_id     BIGINT NOT NULL,

    CONSTRAINT fk_interest_member_id FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_interest_post_id FOREIGN KEY (post_id) REFERENCES post (id),
    CONSTRAINT uk_member_post UNIQUE (member_id, post_id)
);

-- Trading
CREATE TABLE IF NOT EXISTS trading (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    seller_id   BIGINT NOT NULL,
    buyer_id    BIGINT NOT NULL,
    post_id     BIGINT NOT NULL,
    create_at   TIMESTAMP NOT NULL,
    update_at   TIMESTAMP NOT NULL,

    CONSTRAINT fk_trading_seller_id FOREIGN KEY (seller_id) REFERENCES member (id),
    CONSTRAINT fk_trading_buyer_id FOREIGN KEY (buyer_id) REFERENCES member (id),
    CONSTRAINT fk_trading_post_id FOREIGN KEY (post_id) REFERENCES post (id)
);

-- Review
CREATE TABLE IF NOT EXISTS review (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    post_id     BIGINT NOT NULL,
    content     VARCHAR(255) NOT NULL,
    create_at   TIMESTAMP NOT NULL,
    update_at   TIMESTAMP NOT NULL,

    CONSTRAINT fk_review_member_id FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_review_post_id FOREIGN KEY (post_id) REFERENCES post (id)
);

-- Magazine
CREATE TABLE IF NOT EXISTS magazine (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NOT NULL,
    views       INT CHECK ( views >= 0 ) NOT NULL,
    create_at   TIMESTAMP NOT NULL,
    update_at   TIMESTAMP NOT NULL,

    CONSTRAINT fk_magazine_member_id FOREIGN KEY (member_id) REFERENCES member (id)
);

-- Magazine Image
CREATE TABLE IF NOT EXISTS magazine_image (
    image_id      BIGINT NOT NULL ,
    magazine_id   BIGINT NOT NULL,

    PRIMARY KEY (image_id, magazine_id),
    CONSTRAINT fk_magazine_image_image_id FOREIGN KEY (image_id) REFERENCES image (id),
    CONSTRAINT fk_magazine_image_magazine_id FOREIGN KEY (magazine_id) REFERENCES magazine (id)
);

-- Notice
CREATE TABLE IF NOT EXISTS notice (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NOT NULL,
    views       INT CHECK (views >= 0) NOT NULL,
    create_at   TIMESTAMP NOT NULL,
    update_at   TIMESTAMP NOT NULL,

    CONSTRAINT fk_notice_member_id FOREIGN KEY (member_id) REFERENCES member (id)
);


-- Notice Image
CREATE TABLE IF NOT EXISTS notice_image (
    image_id    BIGINT NOT NULL,
    notice_id   BIGINT NOT NULL,

    PRIMARY KEY (image_id, notice_id),
    CONSTRAINT fk_notice_image_image_id FOREIGN KEY (image_id) REFERENCES image (id),
    CONSTRAINT fk_notice_image_notice_id FOREIGN KEY (notice_id) REFERENCES notice (id)
);

-- Answer
CREATE TABLE IF NOT EXISTS answer (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    admin_role  VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NOT NULL,
    create_at   TIMESTAMP NOT NULL,
    update_at   TIMESTAMP NOT NULL
);

-- Inquiry
CREATE TABLE IF NOT EXISTS inquiry (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    answer_id   BIGINT NOT NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NOT NULL,
    create_at   TIMESTAMP NOT NULL,
    update_at   TIMESTAMP NOT NULL,

    CONSTRAINT fk_inquiry_member_id FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_inquiry_answer_id FOREIGN KEY (answer_id) REFERENCES answer (id)
);
