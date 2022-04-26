package tr.com.ga.common.config.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;

@Getter
public enum ProfileEnums {
    DEV("dev"),
    TEST("test"),
    PROD("prod");


    private String name;

    ProfileEnums(String name) {
        this.name=name;
    }

    public boolean profileExists(Environment environment) {
        for (String profile : environment.getActiveProfiles()) {
            return this.getName().equals(profile);
        }
        return false;
    }
}
