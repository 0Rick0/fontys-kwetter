package nl.rickrongen.fontys.kwetter.DAO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by rick on 2/22/17.
 */
class KwetterCollectionImplementationTest extends IKwetterDaoTest<KwetterCollectionImplementation> {
    @Override
    public KwetterCollectionImplementation createInstance() {
        return new KwetterCollectionImplementation();
    }

    @Override
    public void flush() {
        //do nothing
    }
}