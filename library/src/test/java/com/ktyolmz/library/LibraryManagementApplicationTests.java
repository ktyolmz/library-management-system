package com.ktyolmz.library;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectPackages("com.ktyolmz.library")
@ExcludePackages({"com.ktyolmz.library.TestAllServices", "com.ktyolmz.library.TestUtilsSuite", "com.ktyolmz.library.TestBookSuite"})
class LibraryManagementApplicationTests {

}
