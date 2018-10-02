package com.k2.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	K2CommonTests.class,
	K2MemoryTypeSequenceTests.class,
	AbstractInitialisingK2DaoTests.class,
	K2ReflectorTests.class,
	K2SequenceFactoryTests.class,
	AbstractMemoryK2DaoTests.class,
	MemoryK2DaoFactoryTests.class
})
public class K2CommonTestSuite {

}
