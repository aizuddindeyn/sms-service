CREATE TABLE IF NOT EXISTS sms_service_provider (
    id bigint(20) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    service_provider varchar(50) NOT NULL,
    weight int NOT NULL,
    created_time datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time datetime(3) NULL ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sms_log (
    id bigint(20) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    reference varchar(64) NOT NULL,
    mobile varchar(30) NOT NULL,
    message varchar(200) NULL,
    response varchar(1000) NULL,
    status_code varchar(2) NOT NULL,
    service_provider varchar(100) NULL,
    request_type varchar(10) NOT NULL,
    request_reference varchar(64) NULL,
    request_time datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sent_time datetime(3) NULL,
    error bit NOT NULL DEFAULT '0'
);

CREATE INDEX IF NOT EXISTS idx_reference ON sms_log(reference);
CREATE INDEX IF NOT EXISTS idx_mobile ON sms_log(mobile);
CREATE INDEX IF NOT EXISTS idx_request_time ON sms_log(request_time);
CREATE INDEX IF NOT EXISTS idx_sent_time ON sms_log(sent_time);
CREATE INDEX IF NOT EXISTS idx_service_provider ON sms_log(service_provider);