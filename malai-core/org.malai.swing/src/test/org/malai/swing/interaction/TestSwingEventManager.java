package org.malai.swing.interaction;

import java.awt.Component;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.text.JTextComponent;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.BasicEventManager;
import org.malai.interaction.EventHandler;
import org.malai.interaction.TimeoutTransition;
import org.malai.swing.widget.MFrame;



class EventHandlerMock implements EventHandler, SwingEventHandler {
	@Override
	public void onTimeout(final TimeoutTransition timeoutTransition) {/* */}
	@Override
	public void onTextChanged(final JTextComponent textComp) {/* */}
	@Override
	public void onSpinnerChanged(final JSpinner spinner) {/* */}
	@Override
	public void onScroll(final int posX, final int posY, final int direction, final int amount, final int type, final int idHID, final Object src) {/* */}
	@Override
	public void onRelease(final int button, final int x, final int y, final int idHID, final Object source) {/* */}
	@Override
	public void onPressure(final int button, final int x, final int y, final int idHID, final Object source) {/* */}
	@Override
	public void onMove(final int button, final int x, final int y, final boolean pressed, final int idHID, final Object source) {/* */}
	@Override
	public void onMenuItemPressed(final JMenuItem menuItem) {/* */}
	@Override
	public void onKeyRelease(final int key, final char keyChar, final int idHID, final Object object) {/* */}
	@Override
	public void onKeyPressure(final int key, final char keyChar, final int idHID, final Object object) {/* */}
	@Override
	public void onItemSelected(final ItemSelectable itemSelectable) {/* */}
	@Override
	public void onCheckBoxModified(final JCheckBox checkbox) {/* */}
	@Override
	public void onButtonPressed(final AbstractButton button) {/* */}
	@Override
	public void onWindowClosed(final MFrame frame) { /* */ }
	@Override
	public void onTabChanged(final JTabbedPane tabbedPanel) { /* */ }
}


public class TestSwingEventManager extends TestCase {
	protected SwingEventManager manager;
	protected EventHandlerMock eh;

	@Override
	@Before
	public void setUp() {
		manager = new SwingEventManager();
		eh = new EventHandlerMock();
	}

	@Test
	public void testKeyTypedNull() {
		manager.addHandlers(eh);
		manager.keyTyped(null);
	}

	@Test
	public void testMouseClickedNull() {
		manager.addHandlers(eh);
		manager.mousePressed(null);
	}

	@Test
	public void testMouseEnteredNull() {
		manager.addHandlers(eh);
		manager.mouseEntered(null);
	}

	@Test
	public void testMouseExitedNull() {
		manager.addHandlers(eh);
		manager.mouseExited(null);
	}


	@Test
	public void testKeyReleasedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onKeyRelease(final int key, final char keyChar, final int idHID, final Object object) {
				fail();
			}
		};

		manager.addHandlers(eh2);
		manager.keyReleased(null);
	}

	@Test
	public void testKeyReleased() {
		final KeyEvent ke = new KeyEvent(new JButton(), 123, 9871, 124, 634, 'r');
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onKeyRelease(final int key, final char keyChar, final int idHID, final Object object) {
				assertEquals(ke.getKeyCode(), key);
			}
		};

		manager.addHandlers(eh2);
		manager.keyReleased(ke);
	}

	@Test
	public void testKeyPressedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onKeyPressure(final int key, final char keyChar, final int idHID, final Object object) {
				fail();
			}
		};

		manager.addHandlers(eh2);
		manager.keyPressed(null);
	}

	@Test
	public void testKeyPressed() {
		final KeyEvent ke = new KeyEvent(new JButton(), 123, 9871, 124, 634, 'r');
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onKeyPressure(final int key, final char keyChar, final int idHID, final Object object) {
				assertEquals(ke.getKeyCode(), key);
			}
		};

		manager.addHandlers(eh2);
		manager.keyPressed(ke);
	}


	@Test
	public void testStateChangedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onSpinnerChanged(final JSpinner spinner) {fail();}
		};

		manager.addHandlers(eh2);
		manager.stateChanged(null);
	}


	@Test
	public void testStateChangedSpinner() {
		final ChangeEvent ae = new ChangeEvent(new JSpinner());
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onSpinnerChanged(final JSpinner spinner) {
				assertEquals(ae.getSource(), spinner);
			}
		};

		manager.addHandlers(eh2);
		manager.stateChanged(ae);
	}


	@Test
	public void testItemChangedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onItemSelected(final ItemSelectable itemSelectable) {fail();}
		};

		manager.addHandlers(eh2);
		manager.itemStateChanged(null);
	}


	@Test
	public void testItemChangedComboBox() {
		final ItemEvent ae = new ItemEvent(new JComboBox<String>(), 1298, "coucou", 1);
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onItemSelected(final ItemSelectable itemSelectable) {
				assertEquals(ae.getSource(), itemSelectable);
			}
		};

		manager.addHandlers(eh2);
		manager.itemStateChanged(ae);
	}

	@Test
	public void testActionPerformedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onMenuItemPressed(final JMenuItem menuItem) { fail();}
			@Override
			public void onCheckBoxModified(final JCheckBox checkbox) {fail();}
			@Override
			public void onButtonPressed(final AbstractButton button) {fail();}
			@Override
			public void onTextChanged(final JTextComponent textComp) {fail();}
		};

		manager.addHandlers(eh2);
		manager.actionPerformed(null);
	}

	@Test
	public void testActionPerformedButton() {
		final ActionEvent ae = new ActionEvent(new JButton(), 122, "cmd");
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onButtonPressed(final AbstractButton button) {
				assertEquals(ae.getSource(), button);
			}
			@Override
			public void onCheckBoxModified(final JCheckBox checkbox) {fail();}
			@Override
			public void onMenuItemPressed(final JMenuItem menuItem) { fail();}
			@Override
			public void onTextChanged(final JTextComponent textComp) {fail();}
		};

		manager.addHandlers(eh2);
		manager.actionPerformed(ae);
	}

	@Test
	public void testActionPerformedMenuItem() {
		final ActionEvent ae = new ActionEvent(new JMenuItem(), 122, "cmd");
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onButtonPressed(final AbstractButton button) {fail(); }
			@Override
			public void onCheckBoxModified(final JCheckBox checkbox) {fail();}
			@Override
			public void onMenuItemPressed(final JMenuItem menuItem) { assertEquals(ae.getSource(), menuItem); }
			@Override
			public void onTextChanged(final JTextComponent textComp) {fail();}
		};

		manager.addHandlers(eh2);
		manager.actionPerformed(ae);
	}

	@Test
	public void testActionPerformedCheckBox() {
		final ActionEvent ae = new ActionEvent(new JCheckBox(), 122, "cmd");
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onButtonPressed(final AbstractButton button) {fail(); }
			@Override
			public void onCheckBoxModified(final JCheckBox checkbox) {assertEquals(ae.getSource(), checkbox); }
			@Override
			public void onMenuItemPressed(final JMenuItem menuItem) { fail();}
			@Override
			public void onTextChanged(final JTextComponent textComp) {fail();}
		};

		manager.addHandlers(eh2);
		manager.actionPerformed(ae);
	}

	@Test
	public void testActionPerformedTextField() {
		final ActionEvent ae = new ActionEvent(new JTextField(), 122, "cmd");
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onButtonPressed(final AbstractButton button) {fail(); }
			@Override
			public void onCheckBoxModified(final JCheckBox checkbox) {fail();}
			@Override
			public void onMenuItemPressed(final JMenuItem menuItem) { fail();}
			@Override
			public void onTextChanged(final JTextComponent textComp) {assertEquals(ae.getSource(), textComp);}
		};

		manager.addHandlers(eh2);
		manager.actionPerformed(ae);
	}


	@Test
	public void testMouseWheelMovedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onScroll(final int posX, final int posY, final int direction, final int amount, final int type, final int idHID, final Object src) {
				fail();
			}
		};

		manager.addHandlers(eh2);
		manager.mouseWheelMoved(null);
	}

	@Test
	public void testMouseWheelMoved() {
		final MouseWheelEvent mwe = new MouseWheelEvent(new JButton(), 1232, 973, 1234, 8643, 8973, 98732, true, 1, 12454, -1);
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onScroll(final int posX, final int posY, final int direction, final int amount, final int type, final int idHID, final Object src) {
				assertEquals(mwe.getSource(), src);
				assertEquals(mwe.getX(), posX);
				assertEquals(mwe.getY(), posY);
				assertEquals(mwe.getScrollAmount(), amount);
				assertEquals(mwe.getScrollType(), type);
				assertEquals(mwe.getWheelRotation(), direction);
			}
		};

		manager.addHandlers(eh2);
		manager.mouseWheelMoved(mwe);
	}

	@Test
	public void testMouseMovedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onMove(final int button, final int x, final int y, final boolean pressed, final int idHID, final Object source) {
				fail();
			}
		};

		manager.addHandlers(eh2);
		manager.mouseMoved(null);
	}

	@Test
	public void testMouseMoved() {
		final MouseEvent me = new MouseEvent(new JButton(), 101, 123, 98, 12978, 53, 9873, 9733, 12, true, 2);
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onMove(final int button, final int x, final int y, final boolean pressed, final int idHID, final Object source) {
				assertEquals(me.getButton(), button);
				assertEquals(me.getSource(), source);
				assertEquals(me.getX(), x);
				assertEquals(me.getY(), y);
				assertFalse(pressed);
			}
		};

		manager.addHandlers(eh2);
		manager.mouseMoved(me);
	}


	@Test
	public void testMouseDraggedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onMove(final int button, final int x, final int y, final boolean pressed, final int idHID, final Object source) {
				fail();
			}
		};

		manager.addHandlers(eh2);
		manager.mouseDragged(null);
	}

	@Test
	public void testMouseDragged() {
		final MouseEvent me = new MouseEvent(new JButton(), 101, 123, 98, 12978, 53, 9873, 9733, 12, true, 2);
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onMove(final int button, final int x, final int y, final boolean pressed, final int idHID, final Object source) {
				assertEquals(me.getButton(), button);
				assertEquals(me.getSource(), source);
				assertEquals(me.getX(), x);
				assertEquals(me.getY(), y);
				assertTrue(pressed);
			}
		};

		manager.addHandlers(eh2);
		manager.mouseDragged(me);
	}

	@Test
	public void testMouseReleasedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onRelease(final int button, final int x, final int y, final int idHID, final Object source) {
				fail();
			}
		};

		manager.addHandlers(eh2);
		manager.mouseReleased(null);
	}

	@Test
	public void testMouseReleased() {
		final MouseEvent me = new MouseEvent(new JButton(), 101, 123, 98, 12978, 53, 9873, 9733, 12, true, 2);
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onRelease(final int button, final int x, final int y, final int idHID, final Object source) {
				assertEquals(me.getButton(), button);
				assertEquals(me.getSource(), source);
				assertEquals(me.getX(), x);
				assertEquals(me.getY(), y);
			}
		};

		manager.addHandlers(eh2);
		manager.mouseReleased(me);
	}

	@Test
	public void testMousePressedNull() {
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onPressure(final int button, final int x, final int y, final int idHID, final Object source) {
				fail();
			}
		};

		manager.addHandlers(eh2);
		manager.mousePressed(null);
	}

	@Test
	public void testMousePressed() {
		final MouseEvent me = new MouseEvent(new JButton(), 101, 123, 98, 12978, 53, 9873, 9733, 12, true, 2);
		final EventHandler eh2 = new EventHandlerMock() {
			@Override
			public void onPressure(final int button, final int x, final int y, final int idHID, final Object source) {
				assertEquals(me.getButton(), button);
				assertEquals(me.getSource(), source);
				assertEquals(me.getX(), x);
				assertEquals(me.getY(), y);
			}
		};

		manager.addHandlers(eh2);
		manager.mousePressed(me);
	}




	@Test
	public void testRemoveHandlerNull() {
		manager.removeHandler(null);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testRemoveHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final Field field = BasicEventManager.class.getDeclaredField("handlers");
		field.setAccessible(true);
		final List<EventHandler> ehs = (List<EventHandler>) field.get(manager);
		final int size = ehs.size();
		manager.addHandlers(eh);
		manager.removeHandler(eh);
		assertEquals(size, ehs.size());

		boolean found = false;
		for(int i=0; i<ehs.size(); i++)
			if(ehs.get(i)==eh)
				found = true;

		assertFalse(found);
	}



	@Test
	@SuppressWarnings("unchecked")
	public void testAddHandlerNull() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final Field field = BasicEventManager.class.getDeclaredField("handlers");
		field.setAccessible(true);
		final List<EventHandler> ehs = (List<EventHandler>) field.get(manager);
		final int size = ehs.size();
		manager.addHandlers(null);
		assertEquals(size, ehs.size());
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testAddHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final Field field = BasicEventManager.class.getDeclaredField("handlers");
		field.setAccessible(true);
		final List<EventHandler> ehs = (List<EventHandler>) field.get(manager);
		final int size = ehs.size();
		manager.addHandlers(eh);
		assertEquals(size+1, ehs.size());

		boolean found = false;
		for(int i=0; i<ehs.size(); i++)
			if(ehs.get(i)==eh)
				found = true;

		assertTrue(found);
	}



	@Test
	public void testDetachFrom() {
		manager.detachForm(null);
	}


	@Test
	public void testDetachFromActionListenerAbstractButton() {
		final AbstractButton c = new JButton();
		final int cpt = c.getActionListeners().length;
		manager.attachTo(c);
		manager.detachForm(c);
		final Object[] il = c.getActionListeners();
		assertEquals(cpt, il.length);

		boolean found = false;
		for(int i=0; i<il.length; i++)
			if(il[i]==manager)
				found = true;

		assertFalse(found);
	}

	@Test
	public void testDetachFromActionListenerTextField() {
		final JTextField c = new JTextField();
		final int cpt = c.getActionListeners().length;
		manager.attachTo(c);
		manager.detachForm(c);
		final Object[] il = c.getActionListeners();
		assertEquals(cpt, il.length);

		boolean found = false;
		for(int i=0; i<il.length; i++)
			if(il[i]==manager)
				found = true;

		assertFalse(found);
	}

	@Test
	public void testDetachFromItemSelectable() {
		final JComboBox<String> c = new JComboBox<>();
		final int cpt = c.getItemListeners().length;
		manager.attachTo(c);
		manager.detachForm(c);
		final Object[] il = c.getItemListeners();
		assertEquals(cpt, il.length);

		boolean found = false;
		for(int i=0; i<il.length; i++)
			if(il[i]==manager)
				found = true;

		assertFalse(found);
	}

	@Test
	public void testDetachFromChangeListener() {
		final JSpinner c = new JSpinner();
		final int cpt = c.getChangeListeners().length;
		manager.attachTo(c);
		manager.detachForm(c);
		final Object[] cl = c.getChangeListeners();
		assertEquals(cpt, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertFalse(found);
	}


	@Test
	public void testDetachFromMouseListener() {
		final Component c = new JButton();
		final int cpt = c.getMouseListeners().length;
		manager.attachTo(c);
		manager.detachForm(c);
		final Object[] cl = c.getMouseListeners();
		assertEquals(cpt, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertFalse(found);
	}


	@Test
	public void testDetachFromMouseMotionListener() {
		final Component c = new JButton();
		final int cpt = c.getMouseMotionListeners().length;
		manager.attachTo(c);
		manager.detachForm(c);
		final Object[] cl = c.getMouseMotionListeners();
		assertEquals(cpt, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertFalse(found);
	}


	@Test
	public void testDetachFromMouseWheelListener() {
		final Component c = new JButton();
		final int cpt = c.getMouseWheelListeners().length;
		manager.attachTo(c);
		manager.detachForm(c);
		final Object[] cl = c.getMouseWheelListeners();
		assertEquals(cpt, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertFalse(found);
	}


	@Test
	public void testDetachFromKeyListener() {
		final Component c = new JButton();
		final int cpt = c.getKeyListeners().length;
		manager.attachTo(c);
		manager.detachForm(c);
		final Object[] cl = c.getKeyListeners();
		assertEquals(cpt, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertFalse(found);
	}


	@Test
	public void testAttachTo() {
		manager.attachTo(null);
	}


	@Test
	public void testAttachToActionListenerAbstractButton() {
		final AbstractButton c = new JButton();
		final int cpt = c.getActionListeners().length;
		manager.attachTo(c);
		final Object[] il = c.getActionListeners();
		assertEquals(cpt+1, il.length);

		boolean found = false;
		for(int i=0; i<il.length; i++)
			if(il[i]==manager)
				found = true;

		assertTrue(found);
	}

	@Test
	public void testAttachToActionListenerTextField() {
		final JTextField c = new JTextField();
		final int cpt = c.getActionListeners().length;
		manager.attachTo(c);
		final Object[] il = c.getActionListeners();
		assertEquals(cpt+1, il.length);

		boolean found = false;
		for(int i=0; i<il.length; i++)
			if(il[i]==manager)
				found = true;

		assertTrue(found);
	}

	@Test
	public void testAttachToItemSelectable() {
		final JComboBox<String> c = new JComboBox<>();
		final int cpt = c.getItemListeners().length;
		manager.attachTo(c);
		final Object[] il = c.getItemListeners();
		assertEquals(cpt+1, il.length);

		boolean found = false;
		for(int i=0; i<il.length; i++)
			if(il[i]==manager)
				found = true;

		assertTrue(found);
	}

	@Test
	public void testAttachToChangeListener() {
		final JSpinner c = new JSpinner();
		final int cpt = c.getChangeListeners().length;
		manager.attachTo(c);
		final Object[] cl = c.getChangeListeners();
		assertEquals(cpt+1, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertTrue(found);
	}


	@Test
	public void testAttachToMouseListener() {
		final Component c = new JButton();
		final int cpt = c.getMouseListeners().length;
		manager.attachTo(c);
		final Object[] cl = c.getMouseListeners();
		assertEquals(cpt+1, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertTrue(found);
	}


	@Test
	public void testAttachToMouseMotionListener() {
		final Component c = new JButton();
		final int cpt = c.getMouseMotionListeners().length;
		manager.attachTo(c);
		final Object[] cl = c.getMouseMotionListeners();
		assertEquals(cpt+1, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertTrue(found);
	}


	@Test
	public void testAttachToMouseWheelListener() {
		final Component c = new JButton();
		final int cpt = c.getMouseWheelListeners().length;
		manager.attachTo(c);
		final Object[] cl = c.getMouseWheelListeners();
		assertEquals(cpt+1, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertTrue(found);
	}


	@Test
	public void testAttachToKeyListener() {
		final Component c = new JButton();
		final int cpt = c.getKeyListeners().length;
		manager.attachTo(c);
		final Object[] cl = c.getKeyListeners();
		assertEquals(cpt+1, cl.length);

		boolean found = false;
		for(int i=0; i<cl.length; i++)
			if(cl[i]==manager)
				found = true;

		assertTrue(found);
	}
}
