import { Link } from 'react-router-dom';
import styled from 'styled-components';
// import './Nav.css';

function Nav() {
  const current_path = window.location.pathname;

  const NavContainer = styled.div`
    display: flex;
    flex-direction: row;
    font-size: 20px;
    font-weight: 600;
    gap: 16px;
  `;
  const Line = styled.div`
    width: 2px;
    height: fill-available;
    background-color: black;
  `;

  // 현재 위치에 따라 내비게이션 아이템 색상 변경
  const StyledLink = styled(Link)<{ $path?: string }>`
    text-decoration: none;
    color: ${(props) =>
      props.$path === current_path ? 'var(--primary-100)' : 'black'};
  `;

  return (
    <NavContainer>
      <StyledLink className="nav-item" $path="/" to={'/'}>
        코드 에디터
      </StyledLink>
      <Line />
      <StyledLink className="nav-item" $path="/gitlink" to={'/gitlink'}>
        Git Repo
      </StyledLink>
    </NavContainer>
  );
}

export default Nav;
