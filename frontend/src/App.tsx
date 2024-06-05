import './App.css';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Main from './pages/Main';
import GitRepo from './pages/GitRepo';
import Root from './pages/Root';

// color palette는 index.css에 정의되어 있음
function App() {
  // react-router-dom의 BrowserRouter 대신 createBrowserRouter를 사용
  // root 안에 header
  const BrowserRouter = createBrowserRouter([
    {
      path: '/',
      element: <Root />,
      children: [
        {
          path: '/',
          element: <Main />,
          index: true,
        },
        {
          path: '/gitrepo',
          element: <GitRepo />,
        },
      ],
    },
  ]);

  return (
    <div className="App">
      <RouterProvider router={BrowserRouter}></RouterProvider>
    </div>
  );
}

export default App;
