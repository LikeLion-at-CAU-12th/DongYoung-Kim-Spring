-- Member
INSERT INTO MEMBER (USERNAME, EMAIL, AGE, CREATED_AT, UPDATED_AT) VALUES
                                                                      ( '푸앙', 'puang@cau.ac.kr', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP );

-- Category
INSERT INTO CATEGORY (NAME) VALUES
                                ( '일상' ),
                                ( '맛집' ),
                                ( '개발' ),
                                ( '여행' );

-- Article
INSERT INTO article (CREATED_AT, MEMBER_ID, UPDATED_AT, CONTENT, TITLE) VALUES
                                                                 ( CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 'Test Content 1', 'Test Title 1' )