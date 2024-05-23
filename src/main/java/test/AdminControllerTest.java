package test;

import com.empresa.hito2_programacion_hector.AdminController;
import com.empresa.hito2_programacion_hector.DatabaseManagerApp;
import com.empresa.hito2_programacion_hector.Pieza;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    private AdminController adminController;
    private DatabaseManagerApp dbManager;

    @Before
    public void setUp() {
        dbManager = mock(DatabaseManagerApp.class);
        adminController = new AdminController(dbManager);
    }

    @Test
    public void testAnadirPieza() throws Exception {
        Pieza pieza = new Pieza("1", "nombre", "numero_serie", "fabricante", "descripcion", "marcaVehiculo", "modeloVehiculo");

        // Use reflection to access the private method
        Method method = AdminController.class.getDeclaredMethod("anadirPieza", Pieza.class);
        method.setAccessible(true);
        method.invoke(adminController, pieza);

        verify(dbManager, times(1)).addPiece(pieza);
    }

    // Similar tests for other methods
}