-- Schema: webimagegallery
-- DROP SCHEMA webimagegallery;
CREATE SCHEMA webimagegallery;

CREATE SEQUENCE webimagegallery."gallery_users_idUser_seq";
CREATE SEQUENCE webimagegallery."gallery_photosets_idPhotoset_seq";
CREATE SEQUENCE webimagegallery."gallery_photosets_idOwner_seq";
CREATE SEQUENCE webimagegallery."gallery_photos_idPhoto_seq";
CREATE SEQUENCE webimagegallery."gallery_photos_idUser_seq";
CREATE SEQUENCE webimagegallery."gallery_photos-photosets_idPhoto_seq";
CREATE SEQUENCE webimagegallery."gallery_photos-photosets_idPhotoset_seq";

-- Table: webimagegallery.gallery_users
-- DROP TABLE webimagegallery.gallery_users;
CREATE TABLE webimagegallery.gallery_users
(
  "idUser" integer NOT NULL DEFAULT nextval('webimagegallery."gallery_users_idUser_seq"'::regclass),
  login character varying NOT NULL,
  password character varying(128) NOT NULL,
  email character varying NOT NULL,
  name character varying,
  surname character varying,
  "secondName" character varying,
  role character varying NOT NULL DEFAULT "current_user"(),
  "enabled" boolean NOT NULL DEFAULT 'true',
  CONSTRAINT "idUserPK" PRIMARY KEY ("idUser" ),
  CONSTRAINT "emailUnique" UNIQUE (email ),
  CONSTRAINT "loginUnique" UNIQUE (login )
)
WITH (
  OIDS=FALSE
);


-- Table: webimagegallery.gallery_photosets
-- DROP TABLE webimagegallery.gallery_photosets;
CREATE TABLE webimagegallery.gallery_photosets
(
  "idPhotoset" integer NOT NULL DEFAULT nextval('webimagegallery."gallery_photosets_idPhotoset_seq"'::regclass),
  "idOwner" integer NOT NULL DEFAULT nextval('webimagegallery."gallery_photosets_idOwner_seq"'::regclass),
  description text,
  CONSTRAINT "idPhotosetPK" PRIMARY KEY ("idPhotoset" ),
  CONSTRAINT "idOwnerFK" FOREIGN KEY ("idOwner")
      REFERENCES webimagegallery.gallery_users ("idUser") MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);


-- Table: webimagegallery.gallery_photos
-- DROP TABLE webimagegallery.gallery_photos;
CREATE TABLE webimagegallery.gallery_photos
(
  "idPhoto" integer NOT NULL DEFAULT nextval('webimagegallery."gallery_photos_idPhoto_seq"'::regclass),
  "idUser" integer NOT NULL DEFAULT nextval('webimagegallery."gallery_photos_idUser_seq"'::regclass),
  data bytea NOT NULL,
  description character varying,
  CONSTRAINT "idPhotoPK" PRIMARY KEY ("idPhoto" ),
  CONSTRAINT "idOwnerFK" FOREIGN KEY ("idUser")
      REFERENCES webimagegallery.gallery_users ("idUser") MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);


-- Table: webimagegallery.gallery_thumbnails
-- DROP TABLE webimagegallery.gallery_thumbnails;
CREATE TABLE webimagegallery.gallery_thumbnails
(
  "idPhoto" integer NOT NULL,
  data bytea NOT NULL,
  CONSTRAINT "idThumbnailPK" PRIMARY KEY ("idPhoto" )
)
WITH (
  OIDS=FALSE
);


-- Table: webimagegallery."gallery_photos-photosets"
-- DROP TABLE webimagegallery."gallery_photos-photosets";
CREATE TABLE webimagegallery."gallery_photos-photosets"
(
  "idPhoto" integer NOT NULL DEFAULT nextval('webimagegallery."gallery_photos-photosets_idPhoto_seq"'::regclass),
  "idPhotoset" integer NOT NULL DEFAULT nextval('webimagegallery."gallery_photos-photosets_idPhotoset_seq"'::regclass),
  CONSTRAINT "idPhotosetFK" FOREIGN KEY ("idPhotoset")
      REFERENCES webimagegallery.gallery_photosets ("idPhotoset") MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT "idPhotoFK" FOREIGN KEY ("idPhoto")
      REFERENCES webimagegallery.gallery_photos ("idPhoto") MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
