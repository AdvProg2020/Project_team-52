package Model.Interface;

import java.util.List;
import java.util.function.Function;

public interface AddingNew extends Function<List<? extends Packable<?>>,Long> {

    static AddingNew getRegisteringId() {
        return packages -> packages.stream().map(Packable::getId).max(Long::compareTo).orElse(0L) + 1;
    }
}
