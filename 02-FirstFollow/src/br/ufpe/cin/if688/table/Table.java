package br.ufpe.cin.if688.table;


import br.ufpe.cin.if688.parsing.analysis.*;
import br.ufpe.cin.if688.parsing.grammar.*;
import java.util.*;


public final class Table {
    private Table() {    }

    public static Map<LL1Key, List<GeneralSymbol>> createTable(Grammar g) throws NotLL1Exception {
        if (g == null) throw new NullPointerException();

        Map<Nonterminal, Set<GeneralSymbol>> first =
                SetGenerator.getFirst(g);
        Map<Nonterminal, Set<GeneralSymbol>> follow =
                SetGenerator.getFollow(g, first);

        Map<LL1Key, List<GeneralSymbol>> parsingTable =
                new HashMap<LL1Key, List<GeneralSymbol>>();
        Collection<Production> producoes = g.getProductions();
        for(Nonterminal nt : g.getNonterminals()){//vamos olhar para cada nt da table
            for(GeneralSymbol t : first.get(nt)){//Olhando todos os simbolos no first do nt
                List<GeneralSymbol> slot = new ArrayList<GeneralSymbol>();
                for(Production p : producoes){
                    boolean acabou = false;
                    if(p.getNonterminal().equals(nt)){//olhamos apenas para as regras que possuem o nt atual como gerador
                        int indice = 0;
                        List<GeneralSymbol> regra = p.getProduction();
                        GeneralSymbol firstS;
                        while(!acabou){//continua na mesma regra
                            acabou = false;
                            firstS = regra.get(indice);
                            if(firstS instanceof SpecialSymbol){
                                try{
                                    if(!regra.get(indice+1).equals(null)){//caso de ter algo dps do EPISILON
                                        indice++;
                                        acabou = false;
                                    }else{//caso de ser uma regra só com EPISILON
                                        acabou = true;
                                        if(t instanceof SpecialSymbol){
                                            slot = regra;
                                        }
                                    }
                                }catch(Exception e){//ps eu uso o else e o catch, mas sei que ele entra no catch(testei kkkk), mas só pra garantir né
                                    acabou = true;
                                    if(t instanceof SpecialSymbol){
                                        slot = regra;
                                    }
                                }
                            }
                            else if(firstS instanceof Nonterminal){
                                if(first.get(firstS).contains(t)){//caso de o terminal estar no first do nt da regra
                                    acabou = true;
                                    slot = regra;
                                }else if(first.get(firstS).contains(SpecialSymbol.EPSILON)){//caso de n estar no nt, mas ele poder ser vazio
                                    try{
                                        if(!regra.get(indice+1).equals(null)){
                                            indice++;
                                            acabou = false;
                                        }else{
                                            acabou = true;
                                        }
                                    }catch(Exception e){
                                        acabou = true;
                                    }
                                }else{
                                    acabou = true;//ou seja, nessa regra o terminal não está.
                                }
                            }
                            else if(firstS instanceof Terminal){
                                acabou = true;
                                if(firstS.equals(t)){//caso o firstS seja o terminal que estamos buscando
                                    slot = regra;
                                }
                            }
                            else{
                                System.out.println("Símbolo inválido");
                            }
                        }
                    }
                }
                if(t instanceof SpecialSymbol){//caso de ser EPISILON
                    for(GeneralSymbol a : follow.get(nt)){
                        LL1Key aux = new LL1Key(nt,a);
                        parsingTable.put(aux,slot);
                    }
                }else{//aqui colocaremos apenas no terminal
                    LL1Key aux = new LL1Key(nt,t);
                    parsingTable.put(aux,slot);
                }
            }
        }
        /*
         * Implemente aqui o método para retornar a parsing table
         */
        System.out.print(g);
        System.out.println(first);
        System.out.println(follow);
        System.out.println(parsingTable.);
        return parsingTable;
    }
}
