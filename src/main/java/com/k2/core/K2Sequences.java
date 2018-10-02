package com.k2.core;

import com.k2.common.annotation.MetaSequences;
import com.k2.common.sequence.K2SequenceFactory;
import com.k2.core.model.K2Component;
import com.k2.core.model.K2Field;

@MetaSequences
public class K2Sequences extends K2SequenceFactory {

	public K2Sequences() {
		this.initialise(K2Component.class, 36);
		this.initialise(K2Field.class, 18);
	}

}
