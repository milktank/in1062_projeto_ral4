package works.weave.socks.sqlrepo.entities;

import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.filters.FilterClassName;
import org.junit.Test;

public class UnitPojo {
    // Configured for expectation, so we know when a class gets added or removed.
    private static final int EXPECTED_CLASS_COUNT = 2;

    // The package to test
    private static final String POJO_PACKAGE = "works.weave.socks.sqlrepo.entities";

    private final PojoClassFilter filter = new FilterClassName("^((?!Unit).)*$");

}
