package service.dao;

import com.jverstry.DAO.MyPersistenceDAO;
import com.jverstry.Item.MilliTimeItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMyPersistenceDAO {

    @Mock
    private MyPersistenceDAO myPersistenceDAO;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMilliTimeItem() {
        when(myPersistenceDAO.createMilliTimeItem()).thenReturn(100000L);
        long ID = myPersistenceDAO.createMilliTimeItem();
        assertEquals(ID,100000L);
    }

    @Test(timeout = 1)
    public void testCreateMilliTimeItemWithTimeout1() {
        when(myPersistenceDAO.createMilliTimeItem()).thenReturn(100000L);
        long ID = myPersistenceDAO.createMilliTimeItem();
        assertEquals(ID,100000L);
    }

    @Test
    public void testGetMilliTimeItem() {
        when(myPersistenceDAO.createMilliTimeItem()).thenReturn(100000L);
        when(myPersistenceDAO.getMilliTimeItem(100000L)).thenReturn(new MilliTimeItem());
        assertEquals(0,myPersistenceDAO.getMilliTimeItem(100000L).getID());
    }

}
