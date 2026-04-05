O projeto tem o objetivo de implementar diferentes tipos de agentes inteligentes em um ambiente simulado baseado em grid, conforme conceitos estudados em Inteligência Artificial.
Foram desenvolvidos quatro tipos de agentes: agente reativo simples, agente reativo baseado em modelo, agente reativo baseado em objetivo e agente reativo baseado em utilidade, cada agente apresenta um nível crescente de complexidade e capacidade de tomada de decisão.
Formulação do projeto: estados (posições do agente no grid); ações (mover para norte, sul, leste ou oeste); ambiente (matriz n x n contendo células livres, bloqueadas ou com custo); função de transição (atualização da posição do agente após cada movimento); objetivo (varia conforme o agente).
Framework PEAS:
Agente reativo simples: performace (percorrer todas as bordas do ambiente), enviroment (grid não observável porém ele pode saber se está encostando em uma parede), actuators (movimento no grid), sensors (encostando em paredes?)
Agente reativo baseado em modelo: performace(visitar todas as células possíveis), enviroment(parcialmente observável com obstáculos), actuators(movimentos no grid), sensors(percepção local)
Agente reativo baseado em objetivo: performace(alcançar uma posição objetivo), enviroment(parcialmente observável com obstáculos), actuators(movimentos no grid), sensors(percepção local)
Agente reativo baseado em utilidade: performace(alcançar o objetivo com menor custo possível), enviroment(pode ser totalmente ou parcialmente observável), actuators(movimentos no grid), sensors(percepção local ou global)

Modelagem: https://app.diagrams.net/#G1u_7VughQ7hoeoMRE5bD0Q64tXE9LHiys#%7B%22pageId%22%3A%22T6qS64Q2n17S1Yrp-ZFt%22%7D
