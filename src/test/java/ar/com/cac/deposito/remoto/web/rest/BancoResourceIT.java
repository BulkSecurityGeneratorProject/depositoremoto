package ar.com.cac.deposito.remoto.web.rest;

import ar.com.cac.deposito.remoto.DepositoremotoApp;
import ar.com.cac.deposito.remoto.domain.Banco;
import ar.com.cac.deposito.remoto.repository.BancoRepository;
import ar.com.cac.deposito.remoto.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static ar.com.cac.deposito.remoto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.cac.deposito.remoto.domain.enumeration.Acepta;
/**
 * Integration tests for the {@Link BancoResource} REST controller.
 */
@SpringBootTest(classes = DepositoremotoApp.class)
public class BancoResourceIT {

    private static final String DEFAULT_NOMBRE_DE_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DE_FANTASIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIT = "BBBBBBBBBB";

    private static final String DEFAULT_OTROS_DATOS = "AAAAAAAAAA";
    private static final String UPDATED_OTROS_DATOS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final Acepta DEFAULT_DIFERIDOS = Acepta.NO;
    private static final Acepta UPDATED_DIFERIDOS = Acepta.SI;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBancoMockMvc;

    private Banco banco;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BancoResource bancoResource = new BancoResource(bancoRepository);
        this.restBancoMockMvc = MockMvcBuilders.standaloneSetup(bancoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banco createEntity(EntityManager em) {
        Banco banco = new Banco()
            .nombreDeFantasia(DEFAULT_NOMBRE_DE_FANTASIA)
            .numero(DEFAULT_NUMERO)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .cuit(DEFAULT_CUIT)
            .otrosDatos(DEFAULT_OTROS_DATOS)
            .activo(DEFAULT_ACTIVO)
            .diferidos(DEFAULT_DIFERIDOS);
        return banco;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banco createUpdatedEntity(EntityManager em) {
        Banco banco = new Banco()
            .nombreDeFantasia(UPDATED_NOMBRE_DE_FANTASIA)
            .numero(UPDATED_NUMERO)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .cuit(UPDATED_CUIT)
            .otrosDatos(UPDATED_OTROS_DATOS)
            .activo(UPDATED_ACTIVO)
            .diferidos(UPDATED_DIFERIDOS);
        return banco;
    }

    @BeforeEach
    public void initTest() {
        banco = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanco() throws Exception {
        int databaseSizeBeforeCreate = bancoRepository.findAll().size();

        // Create the Banco
        restBancoMockMvc.perform(post("/api/bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banco)))
            .andExpect(status().isCreated());

        // Validate the Banco in the database
        List<Banco> bancoList = bancoRepository.findAll();
        assertThat(bancoList).hasSize(databaseSizeBeforeCreate + 1);
        Banco testBanco = bancoList.get(bancoList.size() - 1);
        assertThat(testBanco.getNombreDeFantasia()).isEqualTo(DEFAULT_NOMBRE_DE_FANTASIA);
        assertThat(testBanco.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testBanco.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testBanco.getCuit()).isEqualTo(DEFAULT_CUIT);
        assertThat(testBanco.getOtrosDatos()).isEqualTo(DEFAULT_OTROS_DATOS);
        assertThat(testBanco.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testBanco.getDiferidos()).isEqualTo(DEFAULT_DIFERIDOS);
    }

    @Test
    @Transactional
    public void createBancoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bancoRepository.findAll().size();

        // Create the Banco with an existing ID
        banco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBancoMockMvc.perform(post("/api/bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banco)))
            .andExpect(status().isBadRequest());

        // Validate the Banco in the database
        List<Banco> bancoList = bancoRepository.findAll();
        assertThat(bancoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBancos() throws Exception {
        // Initialize the database
        bancoRepository.saveAndFlush(banco);

        // Get all the bancoList
        restBancoMockMvc.perform(get("/api/bancos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banco.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreDeFantasia").value(hasItem(DEFAULT_NOMBRE_DE_FANTASIA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT.toString())))
            .andExpect(jsonPath("$.[*].otrosDatos").value(hasItem(DEFAULT_OTROS_DATOS.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].diferidos").value(hasItem(DEFAULT_DIFERIDOS.toString())));
    }
    
    @Test
    @Transactional
    public void getBanco() throws Exception {
        // Initialize the database
        bancoRepository.saveAndFlush(banco);

        // Get the banco
        restBancoMockMvc.perform(get("/api/bancos/{id}", banco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banco.getId().intValue()))
            .andExpect(jsonPath("$.nombreDeFantasia").value(DEFAULT_NOMBRE_DE_FANTASIA.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.cuit").value(DEFAULT_CUIT.toString()))
            .andExpect(jsonPath("$.otrosDatos").value(DEFAULT_OTROS_DATOS.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.diferidos").value(DEFAULT_DIFERIDOS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBanco() throws Exception {
        // Get the banco
        restBancoMockMvc.perform(get("/api/bancos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanco() throws Exception {
        // Initialize the database
        bancoRepository.saveAndFlush(banco);

        int databaseSizeBeforeUpdate = bancoRepository.findAll().size();

        // Update the banco
        Banco updatedBanco = bancoRepository.findById(banco.getId()).get();
        // Disconnect from session so that the updates on updatedBanco are not directly saved in db
        em.detach(updatedBanco);
        updatedBanco
            .nombreDeFantasia(UPDATED_NOMBRE_DE_FANTASIA)
            .numero(UPDATED_NUMERO)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .cuit(UPDATED_CUIT)
            .otrosDatos(UPDATED_OTROS_DATOS)
            .activo(UPDATED_ACTIVO)
            .diferidos(UPDATED_DIFERIDOS);

        restBancoMockMvc.perform(put("/api/bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanco)))
            .andExpect(status().isOk());

        // Validate the Banco in the database
        List<Banco> bancoList = bancoRepository.findAll();
        assertThat(bancoList).hasSize(databaseSizeBeforeUpdate);
        Banco testBanco = bancoList.get(bancoList.size() - 1);
        assertThat(testBanco.getNombreDeFantasia()).isEqualTo(UPDATED_NOMBRE_DE_FANTASIA);
        assertThat(testBanco.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testBanco.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testBanco.getCuit()).isEqualTo(UPDATED_CUIT);
        assertThat(testBanco.getOtrosDatos()).isEqualTo(UPDATED_OTROS_DATOS);
        assertThat(testBanco.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testBanco.getDiferidos()).isEqualTo(UPDATED_DIFERIDOS);
    }

    @Test
    @Transactional
    public void updateNonExistingBanco() throws Exception {
        int databaseSizeBeforeUpdate = bancoRepository.findAll().size();

        // Create the Banco

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBancoMockMvc.perform(put("/api/bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banco)))
            .andExpect(status().isBadRequest());

        // Validate the Banco in the database
        List<Banco> bancoList = bancoRepository.findAll();
        assertThat(bancoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBanco() throws Exception {
        // Initialize the database
        bancoRepository.saveAndFlush(banco);

        int databaseSizeBeforeDelete = bancoRepository.findAll().size();

        // Delete the banco
        restBancoMockMvc.perform(delete("/api/bancos/{id}", banco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Banco> bancoList = bancoRepository.findAll();
        assertThat(bancoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banco.class);
        Banco banco1 = new Banco();
        banco1.setId(1L);
        Banco banco2 = new Banco();
        banco2.setId(banco1.getId());
        assertThat(banco1).isEqualTo(banco2);
        banco2.setId(2L);
        assertThat(banco1).isNotEqualTo(banco2);
        banco1.setId(null);
        assertThat(banco1).isNotEqualTo(banco2);
    }
}
