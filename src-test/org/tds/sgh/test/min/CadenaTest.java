package org.tds.sgh.test.min;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.test.TestBase;


@RunWith(JUnit4.class)
public class CadenaTest extends TestBase
{
	@Test
	public void CadenaControllerNoEsNull()
	{
		assertTrue(
			"El sistema devolvió null. Debe devolver un controlador.",
			this.controllerFactory.createCadenaController() != null);
	}
}
