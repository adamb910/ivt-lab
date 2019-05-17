package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore MockPF;
  private TorpedoStore MockSF;

  @BeforeEach
  public void init(){
    MockPF = mock(TorpedoStore.class);
    MockSF = mock(TorpedoStore.class);
    this.ship = new GT4500(MockPF,MockSF);
  }

  @Test
  public void fireTorpedo_Secondary_Single_Success(){
    when(MockPF.isEmpty()).thenReturn(true);
    when(MockSF.isEmpty()).thenReturn(false);
    when(MockSF.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);
    verify(MockSF, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Primary_Single_Success(){

    when(MockPF.isEmpty()).thenReturn(false);
    when(MockSF.isEmpty()).thenReturn(true);
    when(MockPF.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);
    verify(MockPF, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Primary_Single_Fail(){

    when(MockPF.isEmpty()).thenReturn(true);
    when(MockSF.isEmpty()).thenReturn(false);
    when(MockPF.fire(1)).thenReturn(false);
    when(MockSF.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);
    verify(MockPF, times(0)).fire(1);
    verify(MockSF,times(1)).fire(1 );

  }

  @Test
  public void fireTorpedo_Secondary_Single_Fail(){

    when(MockPF.isEmpty()).thenReturn(false);
    when(MockSF.isEmpty()).thenReturn(true);
    when(MockPF.fire(1)).thenReturn(false);
    when(MockSF.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);
    verify(MockSF, times(0)).fire(1);
    verify(MockPF,times(1)).fire(1 );
  }


  @Test
  public void fireTorpedo_All_Fail(){

    when(MockPF.isEmpty()).thenReturn(true);
    when(MockSF.isEmpty()).thenReturn(true);
    when(MockSF.fire(1)).thenReturn(false);
    when(MockSF.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false, result);
    verify(MockSF, times(0)).fire(1);
    verify(MockPF, times(0)).fire(1);
  }



  @Test
  public void fireTorpedo_All_Success(){

    when(MockPF.isEmpty()).thenReturn(false);
    when(MockSF.isEmpty()).thenReturn(false);
    when(MockPF.fire(1)).thenReturn(true);
    when(MockSF.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertTrue(result);
    verify(MockSF, times(1)).fire(1);
    verify(MockPF, times(1)).fire(1);
  }


  @Test
  public void fireTorpedo_PrimaryLastFiredTrue(){

    ship.wasPrimaryFiredLast = true;
    when(MockPF.isEmpty()).thenReturn(true);
    when(MockSF.isEmpty()).thenReturn(false);
    when(MockPF.fire(0)).thenReturn(true);
    when(MockSF.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertTrue(result);
    verify(MockSF, times(1)).fire(1);
    verify(MockPF, times(0)).fire(1);
  }
}
