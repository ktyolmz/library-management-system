package com.ktyolmz.library;

import com.ktyolmz.library.service.TestBookService;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(TestBookService.class)
public class TestBookSuite {
}
