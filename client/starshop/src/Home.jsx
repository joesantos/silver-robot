import { Formik } from "formik";
import LojaCard from "./components/LojaCard";
import { useState, useEffect } from "react";

async function getLojas() {
  try {
    let response = await fetch("http://localhost:3008/lojas");
    let json = await response.json();

    return json;
  } catch (err) {
    console.error(err.message);
  }
}

function onlyUnique(value, index, self) {
  return self.indexOf(value) === index;
}

const listaAtividades = (lojas) => {
  let atividades = [];
  let uniqueAtividades = [];
  let lojasOrdenadas = [];

  lojas.forEach((loja) => {
    atividades.push(loja.atividade);
  });

  uniqueAtividades = atividades.filter(onlyUnique);
  uniqueAtividades.forEach((atv) => {
    lojasOrdenadas.push({ atividade: atv, lojas: [] });

    lojas.forEach((loja) => {
      if (loja.atividade === atv) {
        var test = lojasOrdenadas.find((loja) => loja.atividade == atv);
        var indexTest = lojasOrdenadas.indexOf(test);
        if (indexTest !== undefined) {
          lojasOrdenadas[indexTest].lojas.push(loja);
        }
      }
    });
  });

  console.log(JSON.stringify(lojasOrdenadas));
  return lojasOrdenadas;
};

function Home() {
  useEffect(() => {
    async function fetchData() {
      const lojasResponse = await getLojas();
      let lojasResponseAlfa = lojasResponse.sort((a, b) =>
        a.nome.localeCompare(b.nome)
      );

      setLojas(lojasResponseAlfa);
      let lojasOrdenadas = listaAtividades(lojasResponse);
      setOrdenadosPorAtividade(lojasOrdenadas);
    }

    fetchData();
  }, []);

  const [lojas, setLojas] = useState([]);
  const [lojasOrdenadas, setLojasOrdenadas] = useState([]);
  const [ordenaPorAtividade, setOrdenaPorAtividade] = useState(false); //false é por alfabético no nome da loja
  const [ordenadosPorAtividade, setOrdenadosPorAtividade] = useState([]);

  return (
    <div className="App">
      <header className="App-header"></header>
      <div className="container text-center">
        <div className="row my-4">
          <div className="col-12">
            <h2>ShopTop</h2>
          </div>
        </div>
        <div className="row my-4">
          <div className="col-12">
            <form>
              <label htmlFor="filtro">Filtrar por: </label>
              <select
                name="filtro"
                onChange={(event) => {
                  if (event.target.value === "false") {
                    setOrdenaPorAtividade(false);
                  } else {
                    setOrdenaPorAtividade(true);
                  }
                }}
              >
                <option value={false}>Ordem alfabética</option>
                <option value={true}>Atividade</option>
              </select>
            </form>
          </div>
        </div>
        <div className="row">
          <div className="col-12"></div>
        </div>
        <div className="row mb-5">
          <div className="col-12">
            {ordenaPorAtividade && ordenadosPorAtividade ? (
              ordenadosPorAtividade.map((obj) => {
                let lojas = obj.lojas.map((loja) => {
                  return (
                    <LojaCard
                      nome={loja.nome}
                      atividade={loja.atividade}
                      uri={loja.uri}
                      isAtiv={true}
                      nomeLoja={loja.nome}
                    ></LojaCard>
                  );
                });
                return (
                  <div className="mb-4">
                    <h3>{obj.atividade}</h3>
                    {lojas}
                  </div>
                );
              })
            ) : lojas ? (
              lojas.map((loja) => (
                <LojaCard
                  nome={loja.nome}
                  atividade={loja.atividade}
                  uri={loja.uri}
                  nomeLoja={loja.nome}
                ></LojaCard>
              ))
            ) : (
              <p>Não há lojas</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
