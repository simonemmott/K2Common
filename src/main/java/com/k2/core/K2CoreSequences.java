package com.k2.core;

import com.k2.common.annotation.MetaSequences;
import com.k2.common.model.K2Component;
import com.k2.common.model.K2Field;
import com.k2.common.sequence.K2SequenceFactory;

@MetaSequences
public class K2CoreSequences extends K2SequenceFactory {

	public K2CoreSequences() {
		this.initialise(K2Component.class, 36);
		this.initialise(K2Field.class, 18);
	}

}
