package br.ueg.si.sige;

import java.util.*;

/**
 *
 * @author silvinha
 */
public class HistoricoDAO {
    
    
    public void atualizar(HistoricoEscolar historico){
        
        new AlunoDAO().salvarAlteracoes(historico.getAluno());
        
        for (Iterator iter = historico.getPenalidades().iterator(); iter.hasNext();){
            Penalidade penalidade = (Penalidade) iter.next();
            PenalidadeDAO dao = new PenalidadeDAO();
            if (dao.buscaPorCodigo(penalidade.getCodigo()) != null){
                dao.salvarAlteracoes(penalidade);
            }else{
                dao.incluir(penalidade);
            }
        }
        
        for (Iterator iter = historico.getBoletins().iterator(); iter.hasNext();){
            Boletim boletim = (Boletim) iter.next();
            BoletimDAO dao = new BoletimDAO();
            if (dao.buscaPorCodigo(boletim.getCodigo()) != null){
                dao.salvarAlteracoes(boletim);
            }else{
                dao.incluir(boletim);
            }
        }
        
        for(Iterator iter = historico.getExcluidos().iterator();iter.hasNext();){
            Object item = iter.next();
            if(item instanceof Penalidade){
                new PenalidadeDAO().excluir((Penalidade)item);
            }else if(item instanceof Boletim){
                new BoletimDAO().excluir((Boletim)item);
            }
        }
        
    }
    
    public HistoricoEscolar buscaPorAluno(Aluno aluno){
        
        HistoricoEscolar historico = new HistoricoEscolar();
        historico.setAluno(aluno);
        Map campos = new HashMap();
        
        campos.put("aluno", aluno);
        
        Set boletins = new BoletimDAO().buscaParametrizada(campos);
        boletins = (boletins==null)?new HashSet():boletins;
        Set penalidades = new PenalidadeDAO().buscaParametrizada(campos);
        penalidades = (penalidades==null)?new HashSet():penalidades;
        
        historico.setPenalidades(penalidades);
        historico.setBoletins(boletins);
        historico.setExcluidos(new HashSet());
        
        return historico;
    }
    
}
