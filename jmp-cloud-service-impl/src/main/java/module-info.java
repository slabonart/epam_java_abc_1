module jmp.cloud.service.impl {

    requires transitive jmp.service.api;
    requires jmp.dto;
    requires jmp.repository;

    exports com.epam.jmp.service.impl;
}
